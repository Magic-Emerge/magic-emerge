package ai.magicemerge.bluebird.app.service;

import ai.magicemerge.bluebird.app.integration.dto.MessageFeedbackDto;
import ai.magicemerge.bluebird.app.service.dto.OpenApiAppInfoDto;
import ai.magicemerge.bluebird.app.service.dto.OpenApiConversationDto;
import ai.magicemerge.bluebird.app.service.dto.OpenApiHistoryMessagesDto;

public interface DifyApiService {

	OpenApiHistoryMessagesDto getHistoryMessage(String user, String conversationId, String appKey);

	Boolean setFeedback(MessageFeedbackDto messageFeedbackDto);

	OpenApiConversationDto getConversations(String user, String lastId, Long limit,  String appKey);

	OpenApiAppInfoDto getAppInfo(String user, String appKey);

}
