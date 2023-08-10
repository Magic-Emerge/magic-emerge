package ai.magicemerge.bluebird.app.dal.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author GygesM
 * @since 2023-06-27
 */
@TableName("sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String username;

    @JsonIgnore
    private String authPassword;
    private String wechat;
    private String avatarUrl;
    private String phoneNumber;
    private Date createAt;
    private Date updateAt;
    private String createBy;
    private String updateBy;
    private String userRole;
    private Boolean isDeleted;
    private String email;
    private String openUserId;
    private Boolean isActive;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthPassword() {
        return authPassword;
    }

    public void setAuthPassword(String authPassword) {
        this.authPassword = authPassword;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOpenUserId() {
        return openUserId;
    }

    public void setOpenUserId(String openUserId) {
        this.openUserId = openUserId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "User{" +
        ", id = " + id +
        ", username = " + username +
        ", authPassword = " + authPassword +
        ", wechat = " + wechat +
        ", avatarUrl = " + avatarUrl +
        ", phoneNumber = " + phoneNumber +
        ", createAt = " + createAt +
        ", updateAt = " + updateAt +
        ", createBy = " + createBy +
        ", updateBy = " + updateBy +
        ", userRole = " + userRole +
        ", isDeleted = " + isDeleted +
        ", email = " + email +
        ", openUserId = " + openUserId +
        ", isActive = " + isActive +
        "}";
    }
}
