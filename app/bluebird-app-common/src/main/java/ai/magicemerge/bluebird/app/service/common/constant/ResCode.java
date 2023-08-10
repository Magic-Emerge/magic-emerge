package ai.magicemerge.bluebird.app.service.common.constant;

public enum ResCode {
	SUCCESS(200, "成功"),
	FAILED(0, "失败"),
	NEED_LOGIN(10000, "需要登录"),
	NO_PERMISSION(20000, "无操作权限"),
	INTERNAL_SERVICE_ERROR(50000, "内部服务出错，请联系管理员")

	;

	private final int code;

	private final String msg;

	ResCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
