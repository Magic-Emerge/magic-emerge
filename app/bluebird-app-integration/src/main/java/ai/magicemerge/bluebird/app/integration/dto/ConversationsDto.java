package ai.magicemerge.bluebird.app.integration.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

@Data
public class ConversationsDto implements Serializable {
	private static final long serialVersionUID = 4012718007153881163L;


	private String id;

	private String name;

	private JSONObject inputs;

	private String status;

	private String introduction;

	private Long created_at;

}
