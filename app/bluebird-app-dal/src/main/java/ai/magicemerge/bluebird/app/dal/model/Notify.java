package ai.magicemerge.bluebird.app.dal.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 应用通知
 * </p>
 *
 * @author GygesM
 * @since 2023-07-09
 */
@TableName("app_notify")
public class Notify implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
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


    private LocalDateTime createAt;

    private LocalDateTime updateAt;


    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWarningMsg() {
        return warningMsg;
    }

    public void setWarningMsg(String warningMsg) {
        this.warningMsg = warningMsg;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public Boolean getIsHandle() {
        return isHandle;
    }

    public void setIsHandle(Boolean isHandle) {
        this.isHandle = isHandle;
    }

    public String getOpenUserId() {
        return openUserId;
    }

    public void setOpenUserId(String openUserId) {
        this.openUserId = openUserId;
    }

    public String getAlertInfo() {
        return alertInfo;
    }

    public void setAlertInfo(String alertInfo) {
        this.alertInfo = alertInfo;
    }

    @Override
    public String toString() {
        return "Notify{" +
        ", id = " + id +
        ", warningMsg = " + warningMsg +
        ", alertType = " + alertType +
        ", isHandle = " + isHandle +
        ", openUserId = " + openUserId +
        ", alertInfo = " + alertInfo +
        "}";
    }
}
