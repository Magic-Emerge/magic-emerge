package ai.magicemerge.bluebird.app.service.common;

import lombok.Data;

@Data
public class OkHttpResponse {

	private int code;

	private String body;

	public OkHttpResponse(int code, String body) {
		this.code = code;
		this.body = body;
	}

}
