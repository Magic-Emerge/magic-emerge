package ai.magicemerge.bluebird.app.integration.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageFeedbackDto implements Serializable {

	private static final long serialVersionUID = -8186858652339005258L;

	private String messageId;

	private String rating;

	private String user;

	private String appKey;

}
