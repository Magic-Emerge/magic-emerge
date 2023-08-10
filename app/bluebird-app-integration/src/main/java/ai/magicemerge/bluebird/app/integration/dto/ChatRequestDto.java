package ai.magicemerge.bluebird.app.integration.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

@Data
public class ChatRequestDto implements Serializable {
	private static final long serialVersionUID = -5818338829418776268L;

	private JSONObject inputs;

	private String query;

	private String response_mode;

	private String conversation_id;

	private String appKey;

	private String appType;

	private String openUserId;

	private String model;
}
