package ai.magicemerge.bluebird.app.service.args;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateUserArgs {
    /**
     * 【0】微信 【1】 手机号+验证码 【2】 邮箱+验证码
     */
    @NotNull(message = "way is null")
    @Range(min = 0, max = 2)
    private Integer way;

    private String phoneNumber;

    private String email;

    private String emailCode;

    private String wechat;

    private String smsCode;
    @NotEmpty(message = "password is empty")
    private String password;

}
