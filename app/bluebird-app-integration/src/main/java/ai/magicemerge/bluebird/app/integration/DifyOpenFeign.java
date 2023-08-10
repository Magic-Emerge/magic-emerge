package ai.magicemerge.bluebird.app.integration;


import ai.magicemerge.bluebird.app.integration.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
		name = "dify-open-api",
		url = "${dify.openapi.url}")
public interface DifyOpenFeign {


	@PostMapping(value = "v1/messages/{message_id}/feedbacks", produces = MediaType.APPLICATION_JSON_VALUE)
	OperateResultDto setFeedbacks(@RequestBody MessageFeedbackDto dto,
	                              @PathVariable(value = "message_id") String messageId,
	                              @RequestHeader(value = "Authorization") String appKey);


	@GetMapping(value = "v1/messages", produces = MediaType.APPLICATION_JSON_VALUE)
	DifyResponse<List<HistoryMessageDto>> getHistoryMessage(@RequestParam(value = "user") String user,
	                               @RequestHeader(value = "Authorization") String appKey,
	                               @RequestParam(value = "conversation_id") String conversationId);



	@GetMapping(value = "v1/conversations", produces = MediaType.APPLICATION_JSON_VALUE)
	DifyResponse<List<ConversationsDto>> getConversations(@RequestParam(value = "user") String user,
	                                                      @RequestParam(value = "last_id") String last_id,
	                                                      @RequestParam(value = "limit") Long limit,
	                                                      @RequestHeader(value = "Authorization") String appKey);


	@GetMapping(value = "v1/parameters", produces = MediaType.APPLICATION_JSON_VALUE)
	AppParametersDto getAppParameters(@RequestParam(value = "user") String user,
	                                  @RequestHeader(value = "Authorization") String appKey);



}
