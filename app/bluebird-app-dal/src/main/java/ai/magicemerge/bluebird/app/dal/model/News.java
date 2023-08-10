package ai.magicemerge.bluebird.app.dal.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 应用咨询
 * </p>
 *
 * @author GygesM
 * @since 2023-07-02
 */
@TableName("app_news")
public class News implements Serializable {

	private static final long serialVersionUID = 1L;
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;

	/**
	 * 新闻标题
	 */
	private String title;
	private String description;

	/**
	 * 新闻创作时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
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


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getNewsTime() {
		return newsTime;
	}

	public void setNewsTime(LocalDateTime newsTime) {
		this.newsTime = newsTime;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public String getNewsSource() {
		return newsSource;
	}

	public void setNewsSource(String newsSource) {
		this.newsSource = newsSource;
	}

	@Override
	public String toString() {
		return "News{" +
				", id = " + id +
				", title = " + title +
				", description = " + description +
				", newsTime = " + newsTime +
				", linkUrl = " + linkUrl +
				", isPublic = " + isPublic +
				", newsSource = " + newsSource +
				"}";
	}
}
