package ai.magicemerge.bluebird.app.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ConversationDto implements Serializable {
	private static final long serialVersionUID = -6987644259971167067L;


	private String id;

	/**
	 * 对话名称
	 */
	private String conversationName;

	/**
	 * 与messages里的conversation_id一致
	 */
	private String conversationId;

	private Date createAt;

	/**
	 * 是否删除
	 */
	private Boolean isDeleted;

	/**
	 * 应用key
	 */
	private String appKey;


	private Long appId;


}
