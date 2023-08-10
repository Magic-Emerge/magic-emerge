package ai.magicemerge.bluebird.app.integration.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ChatResponseDto implements Serializable {
	private static final long serialVersionUID = -1026201532138608384L;

	private String event;

	private String taskId;

	private String id;

	private String answer;

	private String conversationId;

	private String openUserId;

	private String role;

	private Date createTime;
}
