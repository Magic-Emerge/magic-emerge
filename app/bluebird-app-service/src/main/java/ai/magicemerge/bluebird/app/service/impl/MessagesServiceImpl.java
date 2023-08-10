package ai.magicemerge.bluebird.app.service.impl;

import ai.magicemerge.bluebird.app.dal.mapper.MessagesMapper;
import ai.magicemerge.bluebird.app.dal.model.Messages;
import ai.magicemerge.bluebird.app.integration.ChatMessageIntegration;
import ai.magicemerge.bluebird.app.integration.dto.ChatRequestDto;
import ai.magicemerge.bluebird.app.integration.dto.MessageFeedbackDto;
import ai.magicemerge.bluebird.app.service.MessagesService;
import ai.magicemerge.bluebird.app.service.StoreService;
import ai.magicemerge.bluebird.app.service.common.enums.ChatRoleEnum;
import ai.magicemerge.bluebird.app.service.common.enums.ModelInputPriceEnum;
import ai.magicemerge.bluebird.app.service.common.utils.CommonUtils;
import ai.magicemerge.bluebird.app.service.common.utils.WebContextUtil;
import ai.magicemerge.bluebird.app.service.dto.AppStoreDto;
import ai.magicemerge.bluebird.app.service.dto.HistoryMessageDto;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.ModelType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * AI聊天记录 服务实现类
 * </p>
 *
 * @author GygesM
 * @since 2023-06-04
 */
@Slf4j
@Service
public class MessagesServiceImpl extends ServiceImpl<MessagesMapper, Messages> implements MessagesService {

	@Autowired
	private ChatMessageIntegration chatMessageIntegration;

	@Autowired
	private StoreService storeService;


	@Override
	public SseEmitter createSse(String uid) {
		return chatMessageIntegration.createSse(uid);
	}

	@Override
	public void closeSse(String uid) {
		chatMessageIntegration.closeSse(uid);
	}

	@Override
	public void sseChat(String uid, ChatRequestDto chatRequest) {
		Long workspaceId = WebContextUtil.getUserSession().getWorkspaceId();
		String userId = WebContextUtil.getUserSession().getUserId();
		//存储用户角色信息
		Messages messages = new Messages();
		messages.setMessageContent(chatRequest.getQuery());
		messages.setConversationId(chatRequest.getConversation_id());
		messages.setOpenUserId(chatRequest.getOpenUserId());
		messages.setCreatedAt(new Date());
		messages.setRole(ChatRoleEnum.USER.name().toLowerCase());
		messages.setMessageId("");
		messages.setWorkspaceId(workspaceId);
		messages.setAppKey(chatRequest.getAppKey());
		long tokens = CommonUtils.getTokens(chatRequest.getModel(), messages.getMessageContent());
		messages.setTokens(tokens);
		BigDecimal fee = CommonUtils.getInputFee(chatRequest.getModel(), tokens);
		messages.setFee(fee);
		this.save(messages);
		chatMessageIntegration.sseChat(uid, chatRequest, workspaceId, userId);
	}

	@Override
	public List<Messages> getHistoryMessages(HistoryMessageDto historyMessageDto) {
		LambdaQueryWrapper<Messages> queryWrapper = new LambdaQueryWrapper<Messages>()
				.orderByAsc(Messages::getCreatedAt);

		queryWrapper.eq(Messages::getConversationId, historyMessageDto.getConversationId());

		Long workspaceId = WebContextUtil.getUserSession().getWorkspaceId();

		if (Objects.nonNull(workspaceId)) {
			queryWrapper.eq(Messages::getWorkspaceId, workspaceId);
		}
		if (StringUtils.isNotBlank(historyMessageDto.getOpenUserId())) {
			queryWrapper.eq(Messages::getOpenUserId, historyMessageDto.getOpenUserId());
		}
		if (StringUtils.isNotBlank(historyMessageDto.getAppKey())) {
			queryWrapper.eq(Messages::getAppKey, historyMessageDto.getAppKey());
		}
		List<Messages> messages = baseMapper.selectList(queryWrapper);
		return messages;
	}

	@Override
	public Boolean setFeedback(MessageFeedbackDto feedbackDto) {
		Messages messages = new Messages();
		messages.setRating(feedbackDto.getRating());
		messages.setId(feedbackDto.getMessageId());
		int update = baseMapper.updateById(messages);
		return update > 0;

	}

	@Override
	public List<Messages> getLatestMessageWithCurUser(HistoryMessageDto historyMessageDto) {
		Long workspaceId = WebContextUtil.getUserSession().getWorkspaceId();
		LambdaQueryWrapper<Messages> queryWrapper = new LambdaQueryWrapper<Messages>()
				.eq(Messages::getWorkspaceId, workspaceId)
				.eq(Messages::getOpenUserId, historyMessageDto.getOpenUserId())
				.eq(Messages::getAppKey, historyMessageDto.getAppKey())
				.orderByDesc(Messages::getCreatedAt);
		List<Messages> messages = baseMapper.selectList(queryWrapper)
				.stream().limit(2).collect(Collectors.toList());
		return messages;
	}
}
