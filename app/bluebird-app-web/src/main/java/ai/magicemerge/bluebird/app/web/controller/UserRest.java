package ai.magicemerge.bluebird.app.web.controller;

import ai.magicemerge.bluebird.app.dal.model.User;
import ai.magicemerge.bluebird.app.service.UserService;
import ai.magicemerge.bluebird.app.service.args.CreateUserArgs;
import ai.magicemerge.bluebird.app.service.args.LoginArgs;
import ai.magicemerge.bluebird.app.service.args.SendSMSArs;
import ai.magicemerge.bluebird.app.service.common.ApiResponse;
import ai.magicemerge.bluebird.app.service.common.PageEntity;
import ai.magicemerge.bluebird.app.service.common.annotions.JwtScope;
import ai.magicemerge.bluebird.app.service.common.bean.AuthSigRequestDto;
import ai.magicemerge.bluebird.app.service.dto.LoginDto;
import ai.magicemerge.bluebird.app.service.dto.RegisterDto;
import ai.magicemerge.bluebird.app.service.dto.UserDto;
import ai.magicemerge.bluebird.app.web.params.SendSmsRequest;
import cn.hutool.core.lang.Assert;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static ai.magicemerge.bluebird.app.service.common.constant.Const.BASE_API;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author GygesM
 * @since 2023-05-29
 */
@Api(tags = "用户")
@RestController
@RequestMapping(BASE_API + "/user")
@Slf4j
public class UserRest {


    @Autowired
    private UserService userService;


    @JwtScope
    @PostMapping
    public ApiResponse<PageEntity<User>> list(@RequestBody UserDto userDto) {
        if (userDto.getPage() == null) {
           userDto.setPage(1L);
        }
        if (userDto.getPageSize() == null) {
           userDto.setPageSize(10L);
        }
        PageEntity<User> aPage = userService.selectUsersPage(userDto);
        return ApiResponse.ok(aPage);
    }

    @JwtScope
    @PostMapping(value = "/detail")
    public ApiResponse<User> getById(@RequestBody @Validated UserDto userRequest) {
        Assert.notBlank(userRequest.getId(), "user id is not null");
        return ApiResponse.ok(userService.getById(userRequest.getId()));
    }

    @JwtScope
    @PostMapping(value = "/detail/byOpenUserId")
    public ApiResponse<User> getByOpenUserId(@RequestBody @Validated UserDto userRequest) {
        Assert.notBlank(userRequest.getId(), "open user id is not null");
        return ApiResponse.ok(userService.getUserByUserOpenId(userRequest.getOpenUserId()));
    }

    @JwtScope
    @PostMapping(value = "/create")
    public ApiResponse<Object> create(@RequestBody @Validated CreateUserArgs params) {
        User user = userService.createUser(params);
        return ApiResponse.ok(user);
    }

    @JwtScope(ignore = true)
    @PostMapping(value = "/send-sms")
    public ApiResponse<Object> sendSms(@RequestBody @Validated SendSmsRequest params) {
        boolean success = userService.sendSMS(new SendSMSArs(params.getCountryCode(), params.getPhoneNumber(), params.getEmail()));
        return ApiResponse.ok(success);
    }

    @JwtScope
    @PostMapping(value = "/delete/{id}")
    public ApiResponse<Object> delete(@PathVariable("id") String id) {
        return ApiResponse.ok(userService.removeById(id));
    }

    @JwtScope
    @PostMapping(value = "/update")
    public ApiResponse<Object> update(@RequestBody @Validated User params) {
        return ApiResponse.ok(userService.updateById(params));
    }

    @JwtScope(ignore = true)
    @PostMapping(value = "/login")
    public ApiResponse<LoginDto> login(@RequestBody @Validated LoginArgs params) {
        LoginDto response = userService.login(params);;
        return ApiResponse.ok(response);
    }


    @JwtScope(ignore = true)
    @PostMapping(value = "/register")
    public ApiResponse<Object> register(@RequestBody @Validated RegisterDto registerDto) {
        Object response = userService.register(registerDto);
        return ApiResponse.ok(response);
    }


    @JwtScope(ignore = true)
    @PostMapping(value = "/validate-captcha")
    public ApiResponse<Boolean> validateCaptcha(@RequestBody @Validated AuthSigRequestDto authSigRequestDto) {
       Boolean result = userService.checkCaptcha(authSigRequestDto);
       return ApiResponse.ok(result);
    }



}
