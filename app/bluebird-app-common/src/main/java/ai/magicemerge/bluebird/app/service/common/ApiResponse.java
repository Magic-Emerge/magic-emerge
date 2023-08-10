package ai.magicemerge.bluebird.app.service.common;

import ai.magicemerge.bluebird.app.service.common.constant.ResCode;
import lombok.Data;

@Data
public class ApiResponse<T> {

	private Integer code;
	private String errorMsg;
	private T data;

	private ApiResponse(int code, String errorMsg, T data) {
		this.code = code;
		this.errorMsg = errorMsg;
		this.data = data;
	}

	public static<T> ApiResponse<T> ok() {
		return new ApiResponse<T>(ResCode.SUCCESS.getCode(), "", null);
	}

	public static<T> ApiResponse<T> ok(T data) {
		return new ApiResponse<T>(ResCode.SUCCESS.getCode(), "", data);
	}

	public static<T> ApiResponse<T> error(String errorMsg) {
		return new ApiResponse<T>(ResCode.FAILED.getCode(), errorMsg, null);
	}

	public static<T> ApiResponse<T> error(int code, String errorMsg) {
		return new ApiResponse<T>(code, errorMsg, null);
	}
}