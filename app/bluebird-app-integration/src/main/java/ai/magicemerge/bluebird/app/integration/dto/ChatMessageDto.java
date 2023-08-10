package ai.magicemerge.bluebird.app.integration.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ChatMessageDto implements Serializable {
	private static final long serialVersionUID = 8926354691995968954L;

	private String event;

	@JsonAlias(value = "taskId")
	private String task_id;

	private String id;

	private String answer;

	private Long created_at;

	@JsonAlias(value = "conversationId")
	private String conversation_id;

	private String openUserId;

	private String role;

	private Date createTime;





}
