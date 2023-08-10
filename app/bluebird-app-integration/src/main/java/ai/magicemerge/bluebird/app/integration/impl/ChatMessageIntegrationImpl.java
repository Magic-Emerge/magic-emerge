package ai.magicemerge.bluebird.app.integration.impl;

import ai.magicemerge.bluebird.app.client.OpenApiStreamClient;
import ai.magicemerge.bluebird.app.integration.ChatMessageIntegration;
import ai.magicemerge.bluebird.app.integration.DifySSEEventSourceListener;
import ai.magicemerge.bluebird.app.integration.dto.ChatRequestDto;
import ai.magicemerge.bluebird.app.integration.dto.DifyResponse;
import ai.magicemerge.bluebird.app.service.common.ChatCompletion;
import ai.magicemerge.bluebird.app.service.common.LocalCache;
import ai.magicemerge.bluebird.app.service.common.enums.ChatTypeEnum;
import ai.magicemerge.bluebird.app.service.common.exceptions.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Slf4j
@Service
public class ChatMessageIntegrationImpl implements ChatMessageIntegration {


	@Value(value = "${dify.openapi.url}")
	private String difyUrl;

	@Value(value = "${dify.openapi.version}")
	private String apiVersion;

	@Value(value = "${dify.openapi.chat-path}")
	private String apiChatPath;

	@Value(value = "${dify.openapi.completion-path}")
	private String apiCompletionPath;


	@Value(value = "${law.openapi.url}")
	private String chatLowUrl;

	@Value(value = "${law.openapi.chat-path}")
	private String chatLowPath;


	@Override
	public SseEmitter createSse(String uid) {
		//默认30秒超时,设置为0L则永不超时
		SseEmitter sseEmitter = new SseEmitter(0L);
		//完成后回调
		sseEmitter.onCompletion(() -> {
			log.info("[{}]结束连接...................", uid);
//			try {
//				sseEmitter.send(SseEmitter.event().id(uid)
//				.name("finish")
//				.data("[DONE]"));
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			sseEmitter.complete();
			LocalCache.CACHE.remove(uid);
		});
		//超时回调
		sseEmitter.onTimeout(() -> {
			log.info("[{}]连接超时...................", uid);
		});
		//异常回调
		sseEmitter.onError(
				throwable -> {
					try {
						log.info("[{}]连接异常,{}", uid, throwable.toString());
						sseEmitter.send(SseEmitter.event()
								.id(uid)
								.name("发生异常！")
								.data(DifyResponse.builder().content("发生异常请重试！").build())
								.reconnectTime(30000));
						LocalCache.CACHE.put(uid, sseEmitter);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		);
		LocalCache.CACHE.put(uid, sseEmitter);
		log.info("[{}]创建sse连接成功！", uid);
		return sseEmitter;
	}

	@Override
	public void closeSse(String uid) {
		SseEmitter sse = (SseEmitter) LocalCache.CACHE.get(uid);
		if (sse != null) {
			sse.complete();
			//移除
			LocalCache.CACHE.remove(uid);
		}
	}

	@Override
	public void sseChat(String uid, ChatRequestDto chatRequest, Long workspaceId, String userId) {

		SseEmitter sseEmitter = (SseEmitter) LocalCache.CACHE.get(uid);

		if (sseEmitter == null) {
			log.info("聊天消息推送失败uid:[{}],没有创建连接，请重试。", uid);
			throw new BaseException("聊天消息推送失败uid:[{}],没有创建连接，请重试。~");
		}

		DifySSEEventSourceListener listener = new DifySSEEventSourceListener(sseEmitter,
				chatRequest.getAppKey(),
				chatRequest.getOpenUserId(),
				workspaceId,
				userId, chatRequest.getModel());

		String apiUrl = selectAPIURL(chatRequest.getAppType());
		ChatCompletion chatCompletion = null;

		if (ChatTypeEnum.LAW.name().equals(chatRequest.getAppType())) {
			chatCompletion = ChatCompletion.builder()
					.apiUrl(apiUrl)
					.response_mode(chatRequest.getResponse_mode())
					.prompt(chatRequest.getQuery())
					.build();
		}

		if (ChatTypeEnum.DIFY_CHAT.name().equals(chatRequest.getAppType())
				|| ChatTypeEnum.DIFY_TEXT.name().equals(chatRequest.getAppType())) {
			chatCompletion = ChatCompletion.builder()
					.apiUrl(apiUrl)
					.conversation_id(chatRequest.getConversation_id())
					.response_mode(chatRequest.getResponse_mode())
					.query(chatRequest.getQuery())
					.user(chatRequest.getOpenUserId())
					.inputs(chatRequest.getInputs())
					.build();
		}


		OpenApiStreamClient openApiStreamClient = new OpenApiStreamClient(chatRequest.getAppKey());
		openApiStreamClient.streamChatCompletion(chatCompletion, listener);
	}


	private String selectAPIURL(String chatType) {
		if (ChatTypeEnum.DIFY_CHAT.name().equals(chatType)) {
			return difyUrl + apiVersion + apiChatPath;
		}

		if (ChatTypeEnum.DIFY_TEXT.name().equals(chatType)) {
			return difyUrl + apiVersion + apiCompletionPath;
		}

		if (ChatTypeEnum.LAW.name().equals(chatType)) {
			return chatLowUrl + chatLowPath;
		}
		return StringUtils.EMPTY;
	}


}
