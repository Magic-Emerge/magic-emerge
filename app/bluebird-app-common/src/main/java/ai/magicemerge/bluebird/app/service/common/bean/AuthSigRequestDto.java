package ai.magicemerge.bluebird.app.service.common.bean;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class AuthSigRequestDto implements Serializable {
	private static final long serialVersionUID = 4799558068300120252L;

	@NotEmpty(message = "sessionId not empty")
	private String sessionId;

	@NotEmpty(message = "sig not empty")
	private String sig;

	@NotEmpty(message = "token not empty")
	private String token;

	@NotEmpty(message = "scene not empty")
	private String scene;

	@NotEmpty(message = "remoteIp not empty")
	private String remoteIp;

}
