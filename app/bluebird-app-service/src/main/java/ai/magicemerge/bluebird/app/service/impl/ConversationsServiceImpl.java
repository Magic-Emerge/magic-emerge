package ai.magicemerge.bluebird.app.service.impl;

import ai.magicemerge.bluebird.app.dal.mapper.MessagesMapper;
import ai.magicemerge.bluebird.app.dal.model.Conversations;
import ai.magicemerge.bluebird.app.dal.mapper.ConversationsMapper;
import ai.magicemerge.bluebird.app.dal.model.Messages;
import ai.magicemerge.bluebird.app.service.ConversationsService;
import ai.magicemerge.bluebird.app.service.common.utils.CopyUtils;
import ai.magicemerge.bluebird.app.service.common.utils.WebContextUtil;
import ai.magicemerge.bluebird.app.service.dto.ConversationDto;
import ai.magicemerge.bluebird.app.service.dto.ConversationHistoryDto;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 对话列表 服务实现类
 * </p>
 *
 * @author GygesM
 * @since 2023-07-10
 */
@Service
public class ConversationsServiceImpl extends ServiceImpl<ConversationsMapper, Conversations> implements ConversationsService {

	@Autowired
	private MessagesMapper messagesMapper;

	@Override
	public List<Conversations> selectAllList(ConversationDto conversationDto) {

		String openUserId = WebContextUtil.getUserSession().getOpenUserId();

		LambdaQueryWrapper<Conversations> queryWrapper = new LambdaQueryWrapper<Conversations>()
				.eq(Conversations::getIsDeleted, false)
				.eq(Conversations::getAppId, conversationDto.getAppId())
				.eq(Conversations::getOpenUserId, openUserId)
				.orderByDesc(Conversations::getCreateAt);

		if (StringUtils.isNotBlank(conversationDto.getConversationName())) {
			queryWrapper.eq(Conversations::getConversationName, conversationDto.getConversationName());
		}
		List<Conversations> conversationsList = baseMapper.selectList(queryWrapper);
		return conversationsList;
	}

	@Override
	public Boolean deleteConversation(ConversationDto conversationDto) {
		Assert.notNull(conversationDto.getId(), "conversation id is not empty");
		Conversations conversations = new Conversations();
		conversations.setId(conversationDto.getId());
		conversations.setUpdateBy(WebContextUtil.getUserSession().getUserId());
		int deleteById = baseMapper.deleteById(conversations);
		return deleteById > 0;
	}

	@Override
	public ConversationHistoryDto getConversationHistory(ConversationDto conversationDto) {
		Conversations conversations = baseMapper.selectById(conversationDto.getId());
		ConversationHistoryDto historyDto = new ConversationHistoryDto();
		if (Objects.nonNull(conversations)) {
			ConversationDto dto = CopyUtils.copy(conversations, ConversationDto.class);
			historyDto.setConversationDto(dto);

			String openUserId = WebContextUtil.getUserSession().getOpenUserId();

			LambdaQueryWrapper<Messages> lambdaQueryWrapper = new LambdaQueryWrapper<Messages>()
					.eq(Messages::getOpenUserId, openUserId)
					.eq(Messages::getAppKey, conversationDto.getAppKey())
					.eq(Messages::getConversationId, conversationDto.getConversationId());

			List<Messages> messages = messagesMapper.selectList(lambdaQueryWrapper);
			historyDto.setMessages(messages);
		}
		return historyDto;
	}


	@Override
	public boolean save(Conversations entity) {
		String openUserId = WebContextUtil.getUserSession().getOpenUserId();
		entity.setOpenUserId(openUserId);
		entity.setCreateBy(WebContextUtil.getUserSession().getUserId());
		return super.save(entity);
	}

}
