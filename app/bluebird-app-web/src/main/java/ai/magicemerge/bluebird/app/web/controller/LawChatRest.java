package ai.magicemerge.bluebird.app.web.controller;

import ai.magicemerge.bluebird.app.integration.dto.ChatRequestDto;
import ai.magicemerge.bluebird.app.integration.dto.LawSearchMessageDto;
import ai.magicemerge.bluebird.app.integration.dto.LawSearchRequestDto;
import ai.magicemerge.bluebird.app.service.LawApiService;
import ai.magicemerge.bluebird.app.service.MessagesService;
import ai.magicemerge.bluebird.app.service.common.ApiResponse;
import ai.magicemerge.bluebird.app.service.common.constant.ResCode;
import ai.magicemerge.bluebird.app.service.common.enums.ChatTypeEnum;
import ai.magicemerge.bluebird.app.service.common.exceptions.BaseException;
import ai.magicemerge.bluebird.app.service.common.utils.CopyUtils;
import ai.magicemerge.bluebird.app.web.params.ChatRequest;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

import static ai.magicemerge.bluebird.app.service.common.constant.Const.BASE_API;

@Api(tags = "法律GPT聊天")
@RestController
@RequestMapping(BASE_API + "/law")
public class LawChatRest {


	@Autowired
	private MessagesService messagesService;

	@Autowired
	private LawApiService lawApiService;


	/**
	 * 创建sse连接
	 * @return
	 */
	@CrossOrigin
	@GetMapping("/create-law-chat")
	public SseEmitter createConnect(@RequestHeader Map<String, String> headers) {
		String uid = getUid(headers);
		return messagesService.createSse(uid);
	}

	/**
	 * 聊天接口
	 *
	 * @param chatRequest
	 */
	@CrossOrigin
	@PostMapping("/chat")
	public void sseChat(@RequestBody ChatRequest chatRequest, @RequestHeader Map<String, String> headers) {
		String uid = getUid(headers);
		ChatRequestDto chatRequestDto = CopyUtils.copy(chatRequest, ChatRequestDto.class);
		chatRequestDto.setResponse_mode(chatRequest.getResponseMode());
		chatRequestDto.setAppType(ChatTypeEnum.LAW.name());
		messagesService.sseChat(uid, chatRequestDto);
	}





	@PostMapping("/search-law")
	public ApiResponse<LawSearchMessageDto> searchLaw(@RequestBody LawSearchRequestDto lawSearchRequestDto) {
		LawSearchMessageDto lawSearchMessageDto = lawApiService.searchLawGpt(lawSearchRequestDto);
		return ApiResponse.ok(lawSearchMessageDto);
	}


	/**
	 * 获取uid
	 *
	 * @param headers
	 * @return
	 */
	private String getUid(Map<String, String> headers) {
		String uid = headers.get("uid");
		if (StrUtil.isBlank(uid)) {
			throw new BaseException(ResCode.INTERNAL_SERVICE_ERROR.getCode());
		}
		return uid;
	}


}
