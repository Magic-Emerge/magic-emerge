package ai.magicemerge.bluebird.app.service.common.exceptions;

public class AuthenticationException extends BaseException {

	private static final long serialVersionUID = 997406624787533616L;

	public AuthenticationException() {
	}

	public AuthenticationException(int code) {
		super(code);
	}

	public AuthenticationException(String msg) {
		super(msg);
	}

	public AuthenticationException(int code, String message) {
		super(code, message);
	}
}
