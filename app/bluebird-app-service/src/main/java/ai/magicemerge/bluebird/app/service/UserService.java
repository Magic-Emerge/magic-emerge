package ai.magicemerge.bluebird.app.service;

import ai.magicemerge.bluebird.app.service.common.PageEntity;
import ai.magicemerge.bluebird.app.service.common.bean.AuthSigRequestDto;
import ai.magicemerge.bluebird.app.dal.model.User;
import ai.magicemerge.bluebird.app.service.args.CreateUserArgs;
import ai.magicemerge.bluebird.app.service.args.LoginArgs;
import ai.magicemerge.bluebird.app.service.args.SendSMSArs;
import ai.magicemerge.bluebird.app.service.dto.LoginDto;
import ai.magicemerge.bluebird.app.service.dto.RegisterDto;
import ai.magicemerge.bluebird.app.service.dto.UserDto;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jetbrains.annotations.NotNull;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author GygesM
 * @since 2023-05-29
 */
public interface UserService extends IService<User> {

    LoginDto login(@NotNull LoginArgs args);

    Boolean register(RegisterDto registerDto);

    User createUser(@NotNull CreateUserArgs args);

    boolean sendSMS(@NotNull SendSMSArs args);

    Boolean checkCaptcha(AuthSigRequestDto authSigRequestDto);

    User getUserByUserOpenId(String userOpenId);

    PageEntity<User> selectUsersPage(UserDto userDto);

}
