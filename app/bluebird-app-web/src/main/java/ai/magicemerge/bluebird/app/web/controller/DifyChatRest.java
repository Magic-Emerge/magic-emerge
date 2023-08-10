package ai.magicemerge.bluebird.app.web.controller;

import ai.magicemerge.bluebird.app.integration.dto.ChatRequestDto;
import ai.magicemerge.bluebird.app.service.MessagesService;
import ai.magicemerge.bluebird.app.service.common.annotions.JwtScope;
import ai.magicemerge.bluebird.app.service.common.annotions.RateLimit;
import ai.magicemerge.bluebird.app.service.common.enums.ChatTypeEnum;
import ai.magicemerge.bluebird.app.service.common.utils.CopyUtils;
import ai.magicemerge.bluebird.app.web.params.ChatRequest;
import cn.hutool.core.lang.Assert;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static ai.magicemerge.bluebird.app.service.common.constant.Const.BASE_API;

@Api(tags = "Dify Chat接口")
@Controller
@RequestMapping(BASE_API + "/dify")
public class DifyChatRest {

	@Autowired
	private MessagesService messagesService;

	/**
	 * 聊天接口
	 *
	 * @param chatRequest
	 */
	@RateLimit
	@JwtScope
	@PostMapping("/chat-messages")
	public SseEmitter sseChat(@RequestBody ChatRequest chatRequest) {
		String uid = chatRequest.getOpenUserId();
		Assert.notBlank(uid, "user id is not empty");
		SseEmitter sseEmitter = messagesService.createSse(uid);
		ChatRequestDto chatRequestDto = CopyUtils.copy(chatRequest, ChatRequestDto.class);
		chatRequestDto.setConversation_id(chatRequest.getConversationId());
		chatRequestDto.setResponse_mode(chatRequest.getResponseMode());
		chatRequestDto.setAppType(ChatTypeEnum.DIFY_CHAT.name());
		messagesService.sseChat(uid, chatRequestDto);
		return sseEmitter;
	}


	/**
	 * 聊天接口
	 *
	 * @param chatRequest
	 */
	@RateLimit
	@JwtScope
	@PostMapping("/chat-completion")
	public SseEmitter sseChatCompletion(@RequestBody ChatRequest chatRequest) {
		String uid = chatRequest.getOpenUserId();
		Assert.notBlank(uid, "user id is not empty");
		SseEmitter sseEmitter = messagesService.createSse(uid);
		ChatRequestDto chatRequestDto = CopyUtils.copy(chatRequest, ChatRequestDto.class);
		chatRequestDto.setConversation_id(chatRequest.getConversationId());
		chatRequestDto.setResponse_mode(chatRequest.getResponseMode());
		chatRequestDto.setAppType(ChatTypeEnum.DIFY_TEXT.name());
		messagesService.sseChat(uid, chatRequestDto);
		return sseEmitter;
	}

//	@JwtScope
//	@PostMapping("/conversations")
//	public ApiResponse<OpenApiConversationDto> getConverstaions(@RequestBody ConversationsRequest request) {
//		OpenApiConversationDto conversations = difyApiService.getConversations(request.getUser(), request.getLastId(), request.getLimit(),
//				request.getAppKey());
//		if (Objects.nonNull(conversations)) {
//			return ApiResponse.ok(conversations);
//		} else {
//			return ApiResponse.error("获取会话列表失败");
//		}
//	}
//
//	@JwtScope
//	@PostMapping("/app-params")
//	public ApiResponse<OpenApiAppInfoDto> getAppParams(@RequestBody AppParamsRequest request) {
//		OpenApiAppInfoDto appInfo = difyApiService.getAppInfo(request.getUser(), request.getAppKey());
//		if (Objects.nonNull(appInfo)) {
//			return ApiResponse.ok(appInfo);
//		} else {
//			return ApiResponse.error("获取应用参数失败");
//		}
//	}

}
