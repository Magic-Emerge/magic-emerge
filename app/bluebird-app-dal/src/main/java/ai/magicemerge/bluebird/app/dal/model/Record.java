package ai.magicemerge.bluebird.app.dal.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 充值记录
 * </p>
 *
 * @author GygesM
 * @since 2023-06-01
 */
@TableName("recharge_record")
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String userId;
    private String username;

    /**
     * 充值金额
     */
    private BigDecimal rechargeAmount;

    /**
     * 充值时间
     */
    private Date rechargeTime;
    private Integer messageCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(BigDecimal rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public Date getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(Date rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public Integer getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Integer messageCount) {
        this.messageCount = messageCount;
    }

    @Override
    public String toString() {
        return "Record{" +
        ", id = " + id +
        ", userId = " + userId +
        ", username = " + username +
        ", rechargeAmount = " + rechargeAmount +
        ", rechargeTime = " + rechargeTime +
        ", messageCount = " + messageCount +
        "}";
    }
}
