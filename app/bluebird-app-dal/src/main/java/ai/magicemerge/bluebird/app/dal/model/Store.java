package ai.magicemerge.bluebird.app.dal.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author GygesM
 * @since 2023-07-02
 */
@TableName("app_store")
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String signId;
    private Short appType;
    private Short appLevel;
    private Short appState;
    private BigDecimal appPrice;
    private String model;
    private String author;
    private String appVersion;
    private Object expertRating;
    private Object userRating;
    private String tutorialProfile;
    private Long downloadCount;
    private Date createAt;
    private Date updateAt;
    private String createBy;
    private String updateBy;
    @TableLogic(value = "false", delval = "true")
    private Boolean isDeleted;
    private String appKey;
    private Long workspaceId;

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

    public String getAppAvatar() {
        return appAvatar;
    }

    public void setAppAvatar(String appAvatar) {
        this.appAvatar = appAvatar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public Short getAppType() {
        return appType;
    }

    public void setAppType(Short appType) {
        this.appType = appType;
    }

    public Short getAppLevel() {
        return appLevel;
    }

    public void setAppLevel(Short appLevel) {
        this.appLevel = appLevel;
    }

    public Short getAppState() {
        return appState;
    }

    public void setAppState(Short appState) {
        this.appState = appState;
    }

    public BigDecimal getAppPrice() {
        return appPrice;
    }

    public void setAppPrice(BigDecimal appPrice) {
        this.appPrice = appPrice;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public Object getExpertRating() {
        return expertRating;
    }

    public void setExpertRating(Object expertRating) {
        this.expertRating = expertRating;
    }

    public Object getUserRating() {
        return userRating;
    }

    public void setUserRating(Object userRating) {
        this.userRating = userRating;
    }

    public String getTutorialProfile() {
        return tutorialProfile;
    }

    public void setTutorialProfile(String tutorialProfile) {
        this.tutorialProfile = tutorialProfile;
    }

    public Long getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Long downloadCount) {
        this.downloadCount = downloadCount;
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

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public Long getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(Long workspaceId) {
        this.workspaceId = workspaceId;
    }

    public Object getUseCase() {
        return useCase;
    }

    public void setUseCase(Object useCase) {
        this.useCase = useCase;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    @Override
    public String toString() {
        return "Store{" +
        ", id = " + id +
        ", signId = " + signId +
        ", appType = " + appType +
        ", appLevel = " + appLevel +
        ", appState = " + appState +
        ", appPrice = " + appPrice +
        ", model = " + model +
        ", author = " + author +
        ", appVersion = " + appVersion +
        ", expertRating = " + expertRating +
        ", userRating = " + userRating +
        ", tutorialProfile = " + tutorialProfile +
        ", downloadCount = " + downloadCount +
        ", createAt = " + createAt +
        ", updateAt = " + updateAt +
        ", createBy = " + createBy +
        ", updateBy = " + updateBy +
        ", isDeleted = " + isDeleted +
        ", appKey = " + appKey +
        ", workspaceId = " + workspaceId +
        ", useCase = " + useCase +
        ", appName = " + appName +
        ", appDesc = " + appDesc +
        "}";
    }
}
