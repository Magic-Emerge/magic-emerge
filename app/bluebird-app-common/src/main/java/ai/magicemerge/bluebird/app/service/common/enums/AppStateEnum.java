package ai.magicemerge.bluebird.app.service.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AppStateEnum {

	ONLINE((short)1),
	OFFLINE((short) 2),
	CHECKING((short) 3)
	;

	private final Short code;



}
