package ai.magicemerge.bluebird.app.service.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LoginTypeEnum {

	WECHAT_QR_CODE(0),
	PHONE_NUMBER_CODE(1),
	USERNAME_PASSWD(2);

	private final int code;


}
