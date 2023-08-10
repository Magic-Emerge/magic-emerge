package ai.magicemerge.bluebird.app.service.common.exceptions;


import ai.magicemerge.bluebird.app.service.common.constant.ResCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 4671656603710360345L;
	private int code;

	private String msg;

	public BaseException(int code) {
		this(code, null);
	}

	public BaseException(String msg) {
		this(ResCode.FAILED.getCode(), msg);
	}

	public BaseException(int code, String message) {
		this.code = code;
		this.msg = message;
	}

}
