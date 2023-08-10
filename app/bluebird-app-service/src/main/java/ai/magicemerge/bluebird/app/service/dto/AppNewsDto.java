package ai.magicemerge.bluebird.app.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AppNewsDto implements Serializable {
	private static final long serialVersionUID = -1907066756684613321L;

	private String id;

	/**
	 * 新闻标题
	 */
	private String title;


	private String description;

	/**
	 * 新闻创作时间
	 */
	private LocalDateTime newsTime;

	/**
	 * 新闻链接地址
	 */
	private String linkUrl;

	/**
	 * 是否公开
	 */
	private Boolean isPublic;

	/**
	 * 消息来源
	 */
	private String newsSource;

}
