package ai.magicemerge.bluebird.app.service.args;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginArgs {
    /**
     * 【0】微信 【1】手机号+验证码 【2】用户名+密码
     */
    @NotNull(message = "way is null")
    private Integer way;

    private String wechat;

    private String phoneNumber;

    private String smsCode;

    private String password;

    private String email;

    private String emailCode;

    private String username;

}


