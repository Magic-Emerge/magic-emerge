package ai.magicemerge.bluebird.app.service;

import ai.magicemerge.bluebird.app.dal.model.Conversations;
import ai.magicemerge.bluebird.app.service.dto.ConversationDto;
import ai.magicemerge.bluebird.app.service.dto.ConversationHistoryDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 对话列表 服务类
 * </p>
 *
 * @author GygesM
 * @since 2023-07-10
 */
public interface ConversationsService extends IService<Conversations> {

	List<Conversations> selectAllList(ConversationDto conversationDto);

	Boolean deleteConversation(ConversationDto conversationDto);

	ConversationHistoryDto getConversationHistory(ConversationDto conversationDto);

}
