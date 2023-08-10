package ai.magicemerge.bluebird.app.service.impl;

import ai.magicemerge.bluebird.app.dal.mapper.MessagesMapper;
import ai.magicemerge.bluebird.app.dal.model.Messages;
import ai.magicemerge.bluebird.app.integration.DifyOpenFeign;
import ai.magicemerge.bluebird.app.integration.dto.*;
import ai.magicemerge.bluebird.app.service.DifyApiService;
import ai.magicemerge.bluebird.app.service.common.OkHttpResponse;
import ai.magicemerge.bluebird.app.service.common.exceptions.BusinessException;
import ai.magicemerge.bluebird.app.service.common.utils.OkHttpUtils;
import ai.magicemerge.bluebird.app.service.dto.OpenApiAppInfoDto;
import ai.magicemerge.bluebird.app.service.dto.OpenApiConversationDto;
import ai.magicemerge.bluebird.app.service.dto.OpenApiHistoryMessagesDto;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static ai.magicemerge.bluebird.app.service.common.utils.CommonUtils.addTokenPrefix;

@Slf4j
@Service
public class DifyApiServiceImpl implements DifyApiService {

	@Autowired
	private DifyOpenFeign difyOpenFeign;

	@Autowired
	private MessagesMapper messagesMapper;

	@Value(value = "${dify.openapi.url}")
	private String difyUrl;



	@Override
	public OpenApiHistoryMessagesDto getHistoryMessage(String user, String conversationId,
	                                                   String appKey) {
		DifyResponse<List<HistoryMessageDto>> historyMessage =
				difyOpenFeign.getHistoryMessage(user, conversationId, addTokenPrefix(appKey));

		if (Objects.nonNull(historyMessage)) {
			OpenApiHistoryMessagesDto messagesDto = new OpenApiHistoryMessagesDto();
			messagesDto.setLimit(historyMessage.getLimit());
			messagesDto.setHasMore(historyMessage.getHas_more());

			List<HistoryMessageDto> data = historyMessage.getData();
			messagesDto.setHistoryList(data);
			return messagesDto;
		}
		return null;
	}

	@Override
	public Boolean setFeedback(MessageFeedbackDto messageFeedbackDto) {
		Messages messages = new Messages();
		messages.setRating(messageFeedbackDto.getRating());
		LambdaUpdateWrapper<Messages> updateWrapper = new LambdaUpdateWrapper<Messages>()
				.eq(Messages::getMessageId, messageFeedbackDto.getMessageId());
		int update = messagesMapper.update(messages, updateWrapper);
//		if (update > 0) {
//			String url = String.format(difyUrl  + "v1/messages/%s/feedbacks", messageFeedbackDto.getMessageId());
//			Headers headers = new Headers.Builder()
//					.add("Authorization", addTokenPrefix(messageFeedbackDto.getAppKey()))
//					.build();
//
//			OkHttpResponse response = null;
//			try {
//				response = OkHttpUtils.post(url, JSON.toJSONString(messageFeedbackDto), headers);
//			} catch (IOException e) {
//				throw new BusinessException("failed to feedback");
//			}
//			String body = response.getBody();
//			JSONObject json = JSONObject.parseObject(body);
//
//		}
		return update > 0;
	}


	@Override
	public OpenApiConversationDto getConversations(String user, String lastId, Long limit, String appKey) {
		DifyResponse<List<ConversationsDto>> conversations = difyOpenFeign.getConversations(user, lastId, limit,
				addTokenPrefix(appKey));
		if (Objects.nonNull(conversations)) {
			OpenApiConversationDto dto = new OpenApiConversationDto();
			dto.setLimit(conversations.getLimit());
			dto.setHasMore(conversations.getHas_more());

			List<ConversationsDto> historyList = dto.getHistoryList();
			dto.setHistoryList(historyList);
		}
		return null;
	}

	@Override
	public OpenApiAppInfoDto getAppInfo(String user, String appKey) {
		AppParametersDto appParameters = difyOpenFeign.getAppParameters(user, addTokenPrefix(appKey));
		OpenApiAppInfoDto dto = new OpenApiAppInfoDto();
		dto.setOpeningStatement(appParameters.getOpening_statement());

		AppParametersDto.State more_like_this = appParameters.getMore_like_this();
		OpenApiAppInfoDto.State moreLikeThisState = new OpenApiAppInfoDto.State();
		moreLikeThisState.setEnabled(more_like_this.getEnabled());
		dto.setMoreLikeThis(moreLikeThisState);


		AppParametersDto.State suggestedQuestionsAfterAnswer = appParameters.getSuggested_questions_after_answer();
		OpenApiAppInfoDto.State suggestedQuestionsAfterAnswerState = new OpenApiAppInfoDto.State();
		suggestedQuestionsAfterAnswerState.setEnabled(suggestedQuestionsAfterAnswer.getEnabled());

		dto.setUserInputForm(appParameters.getUser_input_form());
		dto.setSuggestedQuestions(appParameters.getSuggested_questions());
		return dto;
	}


}
