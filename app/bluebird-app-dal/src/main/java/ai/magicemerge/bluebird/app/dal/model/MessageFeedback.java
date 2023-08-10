package ai.magicemerge.bluebird.app.dal.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 聊天消息反馈
 * </p>
 *
 * @author GygesM
 * @since 2023-06-02
 */
@TableName("chat_message_feedback")
public class MessageFeedback implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String messageId;
    private String rating;
    private String openUsername;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getOpenUsername() {
        return openUsername;
    }

    public void setOpenUsername(String openUsername) {
        this.openUsername = openUsername;
    }

    @Override
    public String toString() {
        return "MessageFeedback{" +
        ", id = " + id +
        ", messageId = " + messageId +
        ", rating = " + rating +
        ", openUsername = " + openUsername +
        "}";
    }
}
