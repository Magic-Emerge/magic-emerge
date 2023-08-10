package ai.magicemerge.bluebird.app.service.dto;

import ai.magicemerge.bluebird.app.dal.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class RegisterDto extends User {
	private static final long serialVersionUID = -1683998827001031770L;

	@NotBlank(message = "邮箱验证码不能为空")
	private String emailCode;

	@NotBlank(message = "手机验证码不能为空")
	private String smsCode;

	@NotBlank(message = "密码不能为空")
	private String password;

}
