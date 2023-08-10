package ai.magicemerge.bluebird.app.service;

import ai.magicemerge.bluebird.app.dal.model.Messages;
import ai.magicemerge.bluebird.app.integration.dto.ChatRequestDto;
import ai.magicemerge.bluebird.app.integration.dto.MessageFeedbackDto;
import ai.magicemerge.bluebird.app.service.dto.HistoryMessageDto;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

/**
 * <p>
 * AI聊天记录 服务类
 * </p>
 *
 * @author GygesM
 * @since 2023-06-04
 */
public interface MessagesService extends IService<Messages> {


	/**
	 * 创建SSE
	 * @param uid
	 * @return
	 */
	SseEmitter createSse(String uid);

	/**
	 * 关闭SSE
	 * @param uid
	 */
	void closeSse(String uid);

	/**
	 * 客户端发送消息到服务端
	 * @param uid
	 * @param chatRequest
	 */
	void sseChat(String uid, ChatRequestDto chatRequest);


	List<Messages> getHistoryMessages(HistoryMessageDto historyMessageDto);

	Boolean setFeedback(MessageFeedbackDto messageFeedbackDto);

	List<Messages> getLatestMessageWithCurUser(HistoryMessageDto historyMessageDto);

}
