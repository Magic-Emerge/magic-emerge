package ai.magicemerge.bluebird.app.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class HistoryMessageDto implements Serializable {
	private static final long serialVersionUID = 578169246793513556L;

	@Schema(name = "用户ID")
	@NotBlank(message = "user id is not empty")
	private String openUserId;

	@Schema(name = "会话ID")
	private String conversationId;

	@Schema(name = "应用appKey")
	@NotBlank(message = "app key is not empty")
	private String appKey;


}
