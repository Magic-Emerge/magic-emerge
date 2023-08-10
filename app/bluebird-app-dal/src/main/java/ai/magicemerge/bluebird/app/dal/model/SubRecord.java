package ai.magicemerge.bluebird.app.dal.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 应用操作记录
 * </p>
 *
 * @author GygesM
 * @since 2023-06-01
 */
@TableName("app_sub_record")
public class SubRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String appSignId;
    private String appName;
    private BigDecimal appSubPrice;
    private String subUserId;
    private String unsubUserId;
    private Date subAt;
    private Date unsubAt;
    private String unsubReason;
    private Integer appSubPoints;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppSignId() {
        return appSignId;
    }

    public void setAppSignId(String appSignId) {
        this.appSignId = appSignId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public BigDecimal getAppSubPrice() {
        return appSubPrice;
    }

    public void setAppSubPrice(BigDecimal appSubPrice) {
        this.appSubPrice = appSubPrice;
    }

    public String getSubUserId() {
        return subUserId;
    }

    public void setSubUserId(String subUserId) {
        this.subUserId = subUserId;
    }

    public String getUnsubUserId() {
        return unsubUserId;
    }

    public void setUnsubUserId(String unsubUserId) {
        this.unsubUserId = unsubUserId;
    }

    public Date getSubAt() {
        return subAt;
    }

    public void setSubAt(Date subAt) {
        this.subAt = subAt;
    }

    public Date getUnsubAt() {
        return unsubAt;
    }

    public void setUnsubAt(Date unsubAt) {
        this.unsubAt = unsubAt;
    }

    public String getUnsubReason() {
        return unsubReason;
    }

    public void setUnsubReason(String unsubReason) {
        this.unsubReason = unsubReason;
    }

    public Integer getAppSubPoints() {
        return appSubPoints;
    }

    public void setAppSubPoints(Integer appSubPoints) {
        this.appSubPoints = appSubPoints;
    }

    @Override
    public String toString() {
        return "SubRecord{" +
        ", id = " + id +
        ", appSignId = " + appSignId +
        ", appName = " + appName +
        ", appSubPrice = " + appSubPrice +
        ", subUserId = " + subUserId +
        ", unsubUserId = " + unsubUserId +
        ", subAt = " + subAt +
        ", unsubAt = " + unsubAt +
        ", unsubReason = " + unsubReason +
        ", appSubPoints = " + appSubPoints +
        "}";
    }
}
