package ai.magicemerge.bluebird.app.service.impl;

import ai.magicemerge.bluebird.app.client.AliyunReCaptchaClient;
import ai.magicemerge.bluebird.app.client.EmailClient;
import ai.magicemerge.bluebird.app.client.TencentApiClient;
import ai.magicemerge.bluebird.app.dal.mapper.UserMapper;
import ai.magicemerge.bluebird.app.dal.model.Members;
import ai.magicemerge.bluebird.app.dal.model.Record;
import ai.magicemerge.bluebird.app.dal.model.User;
import ai.magicemerge.bluebird.app.dal.model.Workspace;
import ai.magicemerge.bluebird.app.service.MembersService;
import ai.magicemerge.bluebird.app.service.RecordService;
import ai.magicemerge.bluebird.app.service.UserService;
import ai.magicemerge.bluebird.app.service.args.CreateUserArgs;
import ai.magicemerge.bluebird.app.service.args.LoginArgs;
import ai.magicemerge.bluebird.app.service.args.SendSMSArs;
import ai.magicemerge.bluebird.app.service.common.PageEntity;
import ai.magicemerge.bluebird.app.service.common.bean.AuthSigRequestDto;
import ai.magicemerge.bluebird.app.service.common.constant.ResCode;
import ai.magicemerge.bluebird.app.service.common.enums.LoginTypeEnum;
import ai.magicemerge.bluebird.app.service.common.enums.MemberTypeEnum;
import ai.magicemerge.bluebird.app.service.common.enums.SysUserRoleEnum;
import ai.magicemerge.bluebird.app.service.common.exceptions.AuthenticationException;
import ai.magicemerge.bluebird.app.service.common.exceptions.BusinessException;
import ai.magicemerge.bluebird.app.service.common.utils.*;
import ai.magicemerge.bluebird.app.service.config.SecretProperties;
import ai.magicemerge.bluebird.app.service.dto.LoginDto;
import ai.magicemerge.bluebird.app.service.dto.RegisterDto;
import ai.magicemerge.bluebird.app.service.dto.UserDto;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.benmanes.caffeine.cache.Cache;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

import static ai.magicemerge.bluebird.app.service.common.constant.Const.DEFAULT_WORKSPACE;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author GygesM
 * @since 2023-05-29
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Autowired
	private TencentApiClient tencentApiClient;

	@Autowired
	Cache<String, Object> caffeineCache;

	@Resource
	private MailProperties mailProperties;

	@Autowired
	private SecretProperties secretProperties;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private AliyunReCaptchaClient aliyunReCaptchaClient;

	@Autowired
	private MembersService membersService;

	@Autowired
	private RecordService recordService;


	@Value("${app.remote.url}")
	private String remoteUrl;


	@Override
	public LoginDto login(@NotNull LoginArgs args) {
		Integer way = args.getWay();
		String wechat = args.getWechat();
		String phoneNumber = args.getPhoneNumber();
		String password = args.getPassword();
		String code = args.getSmsCode();
		User user = new User();

		if (LoginTypeEnum.WECHAT_QR_CODE.getCode() == way) {
			user = obtainUserByWechat(wechat);
		}
		if (LoginTypeEnum.PHONE_NUMBER_CODE.getCode() == way) {
			user = obtainUserByPhoneAndCode(phoneNumber, code);
		}
		if (LoginTypeEnum.USERNAME_PASSWD.getCode() == way) {
			user = obtainUserByUsernameAndPassword(args.getUsername(), password);
		}
		String token = JwtTokenUtil.createToken(JSON.toJSONString(user));
		List<Workspace> workspaceList = membersService.getWorkspaceList(user.getId());
		return new LoginDto(user, token, workspaceList);
	}


	public User obtainUserByWechat(String wechat) {
		if (StrUtil.isEmpty(wechat)) {
			throw new BusinessException(ResCode.FAILED.getCode(), "wechat is empty");
		}
		User user = getOne(new QueryWrapper<User>().lambda()
				.eq(User::getIsDeleted, false)
				.eq(User::getWechat, wechat));

		if (ObjectUtil.isNull(user)) {
			throw new AuthenticationException(ResCode.FAILED.getCode(), "user is null, wechat is error");
		}

		if (!user.getIsActive()) {
			throw new AuthenticationException("user is not active");
		}

		return user;
	}

	public User obtainUserByPhoneAndCode(String phoneNumber, String code) {
		if (StrUtil.isEmpty(phoneNumber) || StrUtil.isEmpty(code)) {
			throw new BusinessException(ResCode.FAILED.getCode(), "phone or smsCode is empty");
		}
		User user = getOne(new QueryWrapper<User>().lambda()
				.eq(User::getIsDeleted, false)
				.eq(User::getPhoneNumber, phoneNumber));

		if (ObjectUtil.isNull(user)) {
			throw new AuthenticationException(ResCode.FAILED.getCode(), "user is null, phone is error");
		}

		if (!user.getIsActive()) {
			throw new AuthenticationException(ResCode.FAILED.getCode(), "user is not active");
		}

		String key = KeyUtil.generateKeyOfPhoneNumberCode(phoneNumber);
		Object value = caffeineCache.getIfPresent(key);
		if (Objects.isNull(value)) {
			throw new AuthenticationException(ResCode.FAILED.getCode(), "smsCode is not validate, please re-send it");
		}

		if (!StrUtil.equals(code, value.toString())) {
			throw new AuthenticationException(ResCode.FAILED.getCode(), "smsCode is error ");
		}
		return user;
	}

	public User obtainUserByUsernameAndPassword(String username, String password) {
		if (StrUtil.isEmpty(username) || StrUtil.isEmpty(password)) {
			throw new BusinessException(ResCode.FAILED.getCode(), "username or smsCode is empty");
		}
		User user = getOne(new QueryWrapper<User>().lambda()
				.eq(User::getUsername, username)
				.eq(User::getIsDeleted, false)
				.eq(User::getAuthPassword, DesCbcUtil.encode(password, secretProperties.getDesSecretKey(), secretProperties.getDesIv())), true);

		if (Objects.isNull(user)) {
			throw new BusinessException(ResCode.FAILED.getCode(), "user is null, phone or password is error");
		}

		if (!user.getIsActive()) {
			throw new BusinessException("user is not active");
		}

		return user;
	}


	public User createUser(@NotNull CreateUserArgs args) {
		Integer way = args.getWay();
		String wechat = args.getWechat();
		String email = args.getEmail();
		String phoneNumber = args.getPhoneNumber();
		String password = args.getPassword();
		String smsCode = args.getSmsCode();
		String emailCode = args.getEmailCode();
		if (way == 0) {
			if (StrUtil.isEmpty(wechat)) {
				throw new BusinessException(ResCode.FAILED.getCode(), "wechat is empty");
			}
			User user = getOne(new QueryWrapper<User>().lambda().eq(User::getWechat, wechat));
			if (ObjectUtil.isNotNull(user)) {
				throw new BusinessException(ResCode.FAILED.getCode(), "wechat is exist");
			}
		} else if (way == 1) {
			User user = getOne(new QueryWrapper<User>().lambda().eq(User::getPhoneNumber, phoneNumber));
			if (ObjectUtil.isNotNull(user)) {
				throw new BusinessException(ResCode.FAILED.getCode(), "phone is exist");
			}
			String key = KeyUtil.generateKeyOfPhoneNumberCode(phoneNumber);
			Object value = caffeineCache.getIfPresent(key);
			if (Objects.isNull(value)) {
				throw new BusinessException(ResCode.FAILED.getCode(), "cache is not exist smsCode");
			}
			if (!StrUtil.equals(smsCode, value.toString())) {
				throw new BusinessException(ResCode.FAILED.getCode(), "sms code is error");
			}
		} else if (way == 2) {
			User user = getOne(new QueryWrapper<User>().lambda().eq(User::getEmail, email));
			if (ObjectUtil.isNotNull(user)) {
				throw new BusinessException(ResCode.FAILED.getCode(), "email is exist");
			}
			String key = KeyUtil.generateKeyOfEmailCode(email);
			Object value = caffeineCache.getIfPresent(key);
			if (Objects.isNull(value)) {
				throw new BusinessException(ResCode.FAILED.getCode(), "cache is not exist emailCode");
			}
			if (!StrUtil.equals(emailCode, value.toString())) {
				throw new BusinessException(ResCode.FAILED.getCode(), "email code is error");
			}
		}
		User user = CopyUtils.copy(args, User.class);
		String encodePasswd = DesCbcUtil.encode(password, secretProperties.getDesSecretKey(), secretProperties.getDesIv());
		user.setAuthPassword(encodePasswd);
		boolean success = save(user);
		if (!success) {
			throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "Failed to create User");
		}
		return user;
	}

	@Override
	public boolean sendSMS(@NotNull SendSMSArs args) {
		String email = args.getEmail();
		if (StrUtil.isNotEmpty(email)) {
			boolean verified = VerifyUtils.verifyEmailAddress(email);
			if (!verified) {
				throw new BusinessException(ResCode.FAILED.getCode(), "email is illegal!");
			}
			return sendEmail(email);
		}

		String phone = args.getPhoneNumber();
		if (StrUtil.isNotEmpty(phone)) {
			boolean verified = VerifyUtils.verifyPhone(phone);
			if (!verified) {
				throw new BusinessException(ResCode.FAILED.getCode(), "phone is illegal!");
			}
		}
		String countryCode = args.getCountryCode();
		if (StrUtil.isEmpty(countryCode)) {
			throw new BusinessException(ResCode.FAILED.getCode(), "countryCode is empty!");
		}
		return sendSMS(phone, countryCode);
	}

	@Override
	public Boolean checkCaptcha(AuthSigRequestDto authSigRequestDto) {
		return aliyunReCaptchaClient.verifyTheCaptcha(authSigRequestDto);
	}

	@Override
	public User getUserByUserOpenId(String userOpenId) {
		if (StringUtils.isNotBlank(userOpenId)) {
			LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
					.eq(User::getIsDeleted, userOpenId)
					.eq(User::getOpenUserId, userOpenId);
			User user = this.baseMapper.selectOne(queryWrapper);
			if (Objects.isNull(user)) {
				throw new BusinessException(ResCode.FAILED.getCode(), "user is not exist");
			}
			return user;
		}
		return null;
	}

	@Override
	public PageEntity<User> selectUsersPage(UserDto userDto) {
		Page<User> userPage = new Page<>(userDto.getPage(), userDto.getPageSize());

		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
				.eq(User::getIsDeleted, false)
				.orderByDesc(User::getCreateAt);

		if (StringUtils.isNotBlank(userDto.getPhoneNumber())) {
			queryWrapper.like(User::getPhoneNumber, userDto.getPhoneNumber());
		}

		if (StringUtils.isNotBlank(userDto.getUsername())) {
			queryWrapper.like(User::getUsername, userDto.getUsername());
		}

		Page<User> selectPage = baseMapper.selectPage(userPage, queryWrapper);
		PageEntity<User> userPageEntity = PageUtils.listToPage(selectPage.getRecords(), selectPage.getCurrent(), selectPage.getSize(),
				selectPage.getTotal());

		return userPageEntity;
	}

	public boolean sendEmail(String email) {
		String key = KeyUtil.generateKeyOfEmailCode(email);
		String code = SmsUtils.generateCode();
		try {

			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put("email", email);
			valueMap.put("emailCode", code);
			valueMap.put("remoteUrl", remoteUrl);
			Context context = new Context();
			context.setVariables(valueMap);
			String content = templateEngine.process("sendSms", context);

			EmailClient.sendHtmlMail(mailProperties.getUsername(), email, "邮箱注册验证码", content);
			Object value = caffeineCache.getIfPresent(key);
			// 已经发送过，立刻返回
			if (Objects.nonNull(value)) {
				return true;
			}
			caffeineCache.put(key, code);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean sendSMS(String phone, String countryCode) {
		String key = KeyUtil.generateKeyOfPhoneNumberCode(phone);
		Object value = caffeineCache.getIfPresent(key);
		// 已经发送过，立刻返回
		if (Objects.nonNull(value)) {
			return true;
		}
		String phoneNumber = String.format("%s%s", countryCode, phone);
		String code = SmsUtils.generateCode();
		try {
			boolean success = tencentApiClient.SendSMS(phoneNumber, code);
			if (success) {
				// 将验证码保存至本地缓存中
				caffeineCache.put(key, code);
			} else {
				throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "Failed to send sms");
			}
		} catch (TencentCloudSDKException e) {
			throw new BusinessException(ResCode.FAILED.getCode(), ExceptionUtils.getMessage(e));
		}
		return true;
	}


	@Override
	public Boolean register(RegisterDto registerDto) {

		Assert.hasText(registerDto.getEmail(), "email is not empty");

		String key = KeyUtil.generateKeyOfEmailCode(registerDto.getEmail());
		Object value = caffeineCache.getIfPresent(key);

		User phoneUser = getOne(new QueryWrapper<User>().lambda().eq(User::getPhoneNumber, registerDto.getPhoneNumber()));
		if (ObjectUtil.isNotNull(phoneUser)) {
			throw new AuthenticationException(ResCode.FAILED.getCode(), "phone is exist");
		}

		User emailUser = getOne(new QueryWrapper<User>().lambda().eq(User::getEmail, registerDto.getEmail()));
		if (ObjectUtil.isNotNull(emailUser)) {
			throw new AuthenticationException(ResCode.FAILED.getCode(), "email is exist");
		}

		if (!StrUtil.equals(String.valueOf(value), registerDto.getEmailCode())) {
			throw new AuthenticationException(ResCode.FAILED.getCode(), "email code is expired");
		}

		Assert.hasText(registerDto.getPhoneNumber(), "phone number is not empty");

		String phoneKey = KeyUtil.generateKeyOfPhoneNumberCode(registerDto.getPhoneNumber());
		String smsCode = String.valueOf(caffeineCache.getIfPresent(phoneKey));
		if (!StrUtil.equals(smsCode, registerDto.getSmsCode())) {
			throw new AuthenticationException(ResCode.FAILED.getCode(), "sms code is expired");
		}
		Assert.hasText(registerDto.getPassword(), "password is not empty");

		User user = CopyUtils.copy(registerDto, User.class);
		user.setIsActive(false);
		user.setOpenUserId(KeyUtil.generateUsername());
		String encodePasswd = DesCbcUtil.encode(registerDto.getPassword(), secretProperties.getDesSecretKey(), secretProperties.getDesIv());
		user.setAuthPassword(encodePasswd);
		//普通用户
		user.setUserRole(SysUserRoleEnum.USER.name());
		boolean save = save(user);
		if (save) {

			//将用户放入默认空间id为1
			Members members = new Members();
			members.setWorkspaceId(DEFAULT_WORKSPACE);
			members.setUserId(user.getId());
			members.setUserType(MemberTypeEnum.PARTNER.name());
			members.setUsername(user.getUsername());
			members.setIsActive(true);
			membersService.save(members);

			//充值普通用户体验次数
			Record record = new Record();
			record.setMessageCount(200);
			record.setRechargeAmount(BigDecimal.ZERO);
			record.setRechargeTime(new Date());
			record.setUsername(user.getUsername());
			record.setUserId(user.getId());
			recordService.save(record);
			return true;
		}
		return false;
	}

}
