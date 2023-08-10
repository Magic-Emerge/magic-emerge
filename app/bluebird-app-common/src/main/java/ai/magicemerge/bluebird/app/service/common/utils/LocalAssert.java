package ai.magicemerge.bluebird.app.service.common.utils;

import ai.magicemerge.bluebird.app.service.common.constant.ResCode;
import ai.magicemerge.bluebird.app.service.common.exceptions.AuthenticationException;
import ai.magicemerge.bluebird.app.service.common.exceptions.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class LocalAssert {


	public static void isTokenBlank(String val, String msg) {
		if (StringUtils.isBlank(val)) {
			throw new AuthenticationException(ResCode.NEED_LOGIN.getCode(), msg);
		}
	}


	public static void isNull(Object o, String msg) {
		if (Objects.nonNull(o)) {
			throw new BusinessException(ResCode.FAILED.getCode(), msg);
		}
	}

	public static void isBlank(String o, String msg) {
		if (StringUtils.isBlank(o)) {
			throw new BusinessException(ResCode.FAILED.getCode(), msg);
		}
	}

}
