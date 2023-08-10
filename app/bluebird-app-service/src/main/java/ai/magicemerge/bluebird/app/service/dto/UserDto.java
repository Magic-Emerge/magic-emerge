package ai.magicemerge.bluebird.app.service.dto;

import ai.magicemerge.bluebird.app.service.common.PageEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDto extends PageEntity {

	private static final long serialVersionUID = 8195270319482232537L;


	@Schema(name = "id", description = "用户id")
	private String id;

	@Schema(name = "openUserId", description = "用户公开id")
	private String openUserId;

	@Schema(name = "username", description = "用户名")
	private String username;

	@Schema(name = "phoneNumber", description = "手机号")
	private String phoneNumber;



}
