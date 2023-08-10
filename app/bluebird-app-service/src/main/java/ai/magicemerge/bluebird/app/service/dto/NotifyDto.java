package ai.magicemerge.bluebird.app.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class NotifyDto implements Serializable {
	private static final long serialVersionUID = 775879189717398889L;


	private Long id;

	/**
	 * 告警信息
	 */
	private String warningMsg;

	/**
	 * SYSTEM / APP (系统告警/应用告警)
	 */
	private String alertType;

	/**
	 * 是否处理
	 */
	private Boolean isHandle;

	/**
	 * 公开用户id
	 */
	private String openUserId;

	/**
	 * 提示信息：比如系统提示
	 */
	private String alertInfo;

}
