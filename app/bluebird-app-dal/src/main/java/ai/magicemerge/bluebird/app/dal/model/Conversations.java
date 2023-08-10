package ai.magicemerge.bluebird.app.dal.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 对话列表
 * </p>
 *
 * @author GygesM
 * @since 2023-07-10
 */
public class Conversations implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 对话名称
     */
    private String conversationName;

    /**
     * 与messages里的conversation_id一致
     */
    private String conversationId;
    private Date createAt;
    private Date updateAt;
    private String createBy;
    private String updateBy;

    /**
     * 是否删除
     */
    @TableLogic(delval = "true", value = "false")
    private Boolean isDeleted;

    private Long appId;

    private String openUserId;

    public String getOpenUserId() {
        return openUserId;
    }

    public void setOpenUserId(String openUserId) {
        this.openUserId = openUserId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConversationName() {
        return conversationName;
    }

    public void setConversationName(String conversationName) {
        this.conversationName = conversationName;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "Conversations{" +
        ", id = " + id +
        ", conversationName = " + conversationName +
        ", conversationId = " + conversationId +
        ", createAt = " + createAt +
        ", updateAt = " + updateAt +
        ", createBy = " + createBy +
        ", updateBy = " + updateBy +
        ", isDeleted = " + isDeleted +
        "}";
    }
}
