package ai.magicemerge.bluebird.app.dal.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author GygesM
 * @since 2023-06-10
 */
@TableName("app_personal_collection")
public class PersonalCollection implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long appId;
    private String appName;
    private String appRemark;
    private Short appType;
    private String collectOfUserId;
    private Date createAt;
    private Date updateAt;
    private String createBy;
    private String updateBy;
    @TableLogic(value = "false", delval = "true")
    private Boolean isDeleted;
    private Long workspaceId;

    public Short getAppType() {
        return appType;
    }

    public void setAppType(Short appType) {
        this.appType = appType;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppRemark() {
        return appRemark;
    }

    public void setAppRemark(String appRemark) {
        this.appRemark = appRemark;
    }

    public String getCollectOfUserId() {
        return collectOfUserId;
    }

    public void setCollectOfUserId(String collectOfUserId) {
        this.collectOfUserId = collectOfUserId;
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

    public Long getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(Long workspaceId) {
        this.workspaceId = workspaceId;
    }

    @Override
    public String toString() {
        return "PersonalCollection{" +
        ", id = " + id +
        ", appId = " + appId +
        ", appName = " + appName +
        ", appRemark = " + appRemark +
        ", collectOfUserId = " + collectOfUserId +
        ", createAt = " + createAt +
        ", updateAt = " + updateAt +
        ", createBy = " + createBy +
        ", updateBy = " + updateBy +
        ", isDeleted = " + isDeleted +
        ", workspaceId = " + workspaceId +
        "}";
    }
}
