package ai.magicemerge.bluebird.app.web.params;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class AppParamsRequest implements Serializable {
	private static final long serialVersionUID = 3048420262648274154L;

	@Schema(name = "用户标识")
	private String user;

	private String appKey;
}
