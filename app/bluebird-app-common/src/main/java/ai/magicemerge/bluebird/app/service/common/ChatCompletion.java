package ai.magicemerge.bluebird.app.service.common;

import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class ChatCompletion implements Serializable {
	private static final long serialVersionUID = -6298826451400947539L;


	private JSONObject inputs;

	private String apiUrl;

	private String query;

	private String response_mode;

	private String conversation_id;

	private String user;

	private String prompt;


}
