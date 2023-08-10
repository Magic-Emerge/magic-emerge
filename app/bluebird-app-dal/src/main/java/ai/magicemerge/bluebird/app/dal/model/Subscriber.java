package ai.magicemerge.bluebird.app.dal.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 会员信息
 * </p>
 *
 * @author GygesM
 * @since 2023-06-01
 */
@TableName("app_subscriber")
public class Subscriber implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private Long id;
    private String userId;
    private String username;

    /**
     * 是否激活
     */
    private Boolean isActive;

    /**
     * 是否为高级会员
     */
    private Boolean isPremium;

    /**
     * 会员积分
     */
    private Integer points;
    private Date dateOfBirth;

    /**
     * male/famale
     */
    private String gender;
    private String country;
    private String city;

    /**
     * 地址
     */
    private String address;
    private String phoneNumber;
    private Integer wechat;

    /**
     * 会员入住时间
     */
    private Date registrationDate;
    private Date createAt;
    private Date updateAt;
    private String createBy;
    private String updateBy;
    private Boolean isDeleted;

    /**
     * 1: 低 2: 中 3: 高
     */
    private Short membershipLevel;

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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(Boolean isPremium) {
        this.isPremium = isPremium;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getWechat() {
        return wechat;
    }

    public void setWechat(Integer wechat) {
        this.wechat = wechat;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
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

    public Short getMembershipLevel() {
        return membershipLevel;
    }

    public void setMembershipLevel(Short membershipLevel) {
        this.membershipLevel = membershipLevel;
    }

    @Override
    public String toString() {
        return "Subscriber{" +
        ", id = " + id +
        ", userId = " + userId +
        ", username = " + username +
        ", isActive = " + isActive +
        ", isPremium = " + isPremium +
        ", points = " + points +
        ", dateOfBirth = " + dateOfBirth +
        ", gender = " + gender +
        ", country = " + country +
        ", city = " + city +
        ", address = " + address +
        ", phoneNumber = " + phoneNumber +
        ", wechat = " + wechat +
        ", registrationDate = " + registrationDate +
        ", createAt = " + createAt +
        ", updateAt = " + updateAt +
        ", createBy = " + createBy +
        ", updateBy = " + updateBy +
        ", isDeleted = " + isDeleted +
        ", membershipLevel = " + membershipLevel +
        "}";
    }
}
