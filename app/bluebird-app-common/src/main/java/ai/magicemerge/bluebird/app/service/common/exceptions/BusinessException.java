package ai.magicemerge.bluebird.app.service.common.exceptions;

import ai.magicemerge.bluebird.app.service.common.constant.ResCode;

/**
 *  业务异常
 */
public class BusinessException extends BaseException {

	private static final long serialVersionUID = -1560971844641411065L;

	public BusinessException() {
	}

	public BusinessException(int code) {
		super(code);
	}

	public BusinessException(String msg) {
		super(msg);
	}

	public BusinessException(int code, String message) {
		super(code, message);
	}
}
