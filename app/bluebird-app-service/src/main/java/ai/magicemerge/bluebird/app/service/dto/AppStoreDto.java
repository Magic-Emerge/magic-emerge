package ai.magicemerge.bluebird.app.service.dto;

import ai.magicemerge.bluebird.app.service.common.PageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class AppStoreDto extends PageEntity {

	private static final long serialVersionUID = 4856142670904189550L;

	private Long id;
	private String signId;
	private Short appType;
	private Short appLevel;
	private Short appState;
	private BigDecimal appPrice;
	private String model;
	private String appVersion;
	private Float expertRating;
	private Float userRating;
	private String tutorialProfile;
	private Date createAt;
	private String createBy;
	private String appKey;


	/**
	 * 使用案例
	 */
	private Object useCase;

	/**
	 * 应用名称
	 */
	private String appName;

	/**
	 * 应用描述
	 */
	private String appDesc;


	private String appAvatar;

}
