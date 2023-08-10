package ai.magicemerge.bluebird.app.dal.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author GygesM
 * @since 2023-06-07
 */
@TableName("law_search")
public class Search implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String searchTextId;
    private String response;
    private String userId;
    private Date searchTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearchTextId() {
        return searchTextId;
    }

    public void setSearchTextId(String searchTextId) {
        this.searchTextId = searchTextId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(Date searchTime) {
        this.searchTime = searchTime;
    }

    @Override
    public String toString() {
        return "Search{" +
        ", id = " + id +
        ", searchTextId = " + searchTextId +
        ", response = " + response +
        ", userId = " + userId +
        ", searchTime = " + searchTime +
        "}";
    }
}
