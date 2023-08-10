package ai.magicemerge.bluebird.app.dal.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * AI聊天记录
 * </p>
 *
 * @author GygesM
 * @since 2023-06-04
 */
@TableName("chat_messages")
public class Messages implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.ASSIGN_ID)
	private String id;

	/**
	 * 消息ID
	 */
	private String messageId;

	/**
	 * 创建时间
	 */
	private Date createdAt;

	/**
	 * 会话ID
	 */
	private String conversationId;

	/**
	 * 输入参数
	 */
	private Object inputs;

	/**
	 * 反馈
	 */
	private Object rating;
	private String role;
	private String messageContent;

	/**
	 * 该名称用于标识dify请求对应user名
	 */
	private String openUserId;

	private String appKey;

	private Long tokens;

	private Long workspaceId;


	private BigDecimal fee;

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public Long getWorkspaceId() {
		return workspaceId;
	}

	public void setWorkspaceId(Long workspaceId) {
		this.workspaceId = workspaceId;
	}

	public Long getTokens() {
		return tokens;
	}

	public void setTokens(Long tokens) {
		this.tokens = tokens;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}

	public Object getInputs() {
		return inputs;
	}

	public void setInputs(Object inputs) {
		this.inputs = inputs;
	}

	public Object getRating() {
		return rating;
	}

	public void setRating(Object rating) {
		this.rating = rating;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getOpenUserId() {
		return openUserId;
	}

	public void setOpenUserId(String openUserId) {
		this.openUserId = openUserId;
	}

	@Override
	public String toString() {
		return "Messages{" +
				", id = " + id +
				", messageId = " + messageId +
				", createdAt = " + createdAt +
				", conversationId = " + conversationId +
				", inputs = " + inputs +
				", rating = " + rating +
				", role = " + role +
				", messageContent = " + messageContent +
				", openUserId = " + openUserId +
				", appKey = " + appKey +
				"}";
	}
}
