package ai.magicemerge.bluebird.app.dal.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 工作空间
 * </p>
 *
 * @author GygesM
 * @since 2023-06-01
 */
public class Workspace implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 空间名称
     */
    private String name;
    private Boolean isPublic;

    /**
     * 是否启用
     */
    private Boolean isValid;

    /**
     * 描述
     */
    private String remark;
    private String createBy;
    private Date createAt;
    private String updateBy;
    private Date updateAt;
    private Boolean isDeleted;


    private String managerId;

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "Workspace{" +
        ", id = " + id +
        ", name = " + name +
        ", isPublic = " + isPublic +
        ", isValid = " + isValid +
        ", remark = " + remark +
        ", createBy = " + createBy +
        ", createAt = " + createAt +
        ", updateBy = " + updateBy +
        ", updateAt = " + updateAt +
        ", isDeleted = " + isDeleted +
        "}";
    }
}
