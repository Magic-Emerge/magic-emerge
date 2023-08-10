package ai.magicemerge.bluebird.app.integration.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

@Data
public class HistoryMessageDto implements Serializable {
	private static final long serialVersionUID = 5583534625642401516L;

	private String id;

	private String conversation_id;

	private JSONObject inputs;

	private String query;

	private String answer;

	private MessageFeedbackDto feedback;

	private Long create_at;

}
