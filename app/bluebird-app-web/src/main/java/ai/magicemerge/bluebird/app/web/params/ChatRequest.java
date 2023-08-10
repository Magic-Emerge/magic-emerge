package ai.magicemerge.bluebird.app.web.params;

import com.alibaba.fastjson.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class ChatRequest implements Serializable {

	private static final long serialVersionUID = 5387465016758594115L;


	@Schema(name = "参数输入")
	private JSONObject inputs;

	@Schema(name = "用户输入")
	private String query;

	@Schema(name = "返回模式: blocking or streaming")
	private String responseMode;

	@Schema(name = "会话id")
	private String conversationId;


	@Schema(name = "dify 应用key")
	private String appKey;

	@Schema(name = "公开user id")
	private String openUserId;

	@Schema(name = "lawGPT, 提示词")
	private String prompt;

	@Schema(name = "模型")
	private String model;

}
