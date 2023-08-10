package ai.magicemerge.bluebird.app.dal.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 应用付费记录
 * </p>
 *
 * @author GygesM
 * @since 2023-06-01
 */
@TableName("app_payment_record")
public class PaymentRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long appId;

    /**
     * 应用名称
     */
    private String appName;
    private String userId;
    private String username;

    /**
     * 付费金额
     */
    private BigDecimal fee;

    /**
     * 付费时间
     */
    private Date paymentTime;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    @Override
    public String toString() {
        return "PaymentRecord{" +
        ", id = " + id +
        ", appId = " + appId +
        ", appName = " + appName +
        ", userId = " + userId +
        ", username = " + username +
        ", fee = " + fee +
        ", paymentTime = " + paymentTime +
        "}";
    }
}
