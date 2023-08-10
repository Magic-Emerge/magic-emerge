package ai.magicemerge.bluebird.app.integration;

import ai.magicemerge.bluebird.app.dal.model.Messages;
import ai.magicemerge.bluebird.app.dal.utils.ChargeRecordUtils;
import ai.magicemerge.bluebird.app.dal.utils.MessagesUtils;
import ai.magicemerge.bluebird.app.integration.dto.ChatMessageDto;
import ai.magicemerge.bluebird.app.integration.dto.ChatResponseDto;
import ai.magicemerge.bluebird.app.service.common.enums.ChatRoleEnum;
import ai.magicemerge.bluebird.app.service.common.utils.CommonUtils;
import ai.magicemerge.bluebird.app.service.common.utils.CopyUtils;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class DifySSEEventSourceListener extends EventSourceListener {

	private long tokens;

	private final SseEmitter sseEmitter;

	private final String appKey;

	private final String openUserId;

	private final Long workspaceId;

	private final String userId;

	private final String model;

	private final List<String> messageLines = new ArrayList<>();

	private String messageId;

	private String conversationId;

	private Long createTime;



	public DifySSEEventSourceListener(SseEmitter sseEmitter, String appKey, String openUserId,
	                                  Long workspaceId, String userId, String model) {
		this.sseEmitter = sseEmitter;
		this.appKey = appKey;
		this.openUserId = openUserId;
		this.workspaceId = workspaceId;
		this.userId = userId;
		this.model = model;
	}


	public void onOpen(EventSource eventSource, Response response) {
		log.info("Dify建立sse连接...");
	}


	@SneakyThrows
	@Override
	public void onEvent(EventSource eventSource, String id, String type, String data) {
		log.info("Dify返回数据：{}", data);
		tokens += 1;
		ChatMessageDto chatMessageDto = JSONObject.parseObject(data, ChatMessageDto.class);
		messageId = chatMessageDto.getId();
		conversationId = chatMessageDto.getConversation_id();
		createTime = chatMessageDto.getCreated_at();
		messageLines.add(chatMessageDto.getAnswer());
		try {
			ChatResponseDto chatResponseDto = CopyUtils.copy(chatMessageDto, ChatResponseDto.class);
			chatResponseDto.setOpenUserId(openUserId);
			chatResponseDto.setCreateTime(DateUtil.date(chatMessageDto.getCreated_at() * 1000));
			chatResponseDto.setRole(ChatRoleEnum.ASSISTANT.name().toLowerCase());
			chatResponseDto.setTaskId(chatMessageDto.getTask_id());
			chatResponseDto.setConversationId(chatMessageDto.getConversation_id());
			sseEmitter.send(SseEmitter.event()
					.data(chatResponseDto));
		} catch (Exception e) {
			log.error("sse信息推送失败！{}", ExceptionUtils.getMessage(e));
			eventSource.cancel();
		}
	}


	@Override
	public void onClosed(EventSource eventSource) {
		log.info("Dify返回数据结束了");
		log.info("流式输出返回值总共{}tokens", tokens() - 2);
		log.info("Dify关闭sse连接...");

		String completeData = String.join("", messageLines);
		Messages messages = new Messages();
		messages.setMessageContent(completeData);
		messages.setRole(ChatRoleEnum.ASSISTANT.name().toLowerCase());
		messages.setMessageId(messageId);
		messages.setConversationId(conversationId);
		messages.setCreatedAt(DateUtil.date(createTime * 1000));
		messages.setAppKey(appKey);
		messages.setTokens(tokens);
		messages.setWorkspaceId(workspaceId);
		messages.setOpenUserId(openUserId);
		BigDecimal fee = CommonUtils.getOutputFee(model, tokens);
		messages.setFee(fee);

		MessagesUtils.saveMessages(messages);
		// 传输完成后自动关闭sse
		messageLines.clear();
		// 体验次数减一
		ChargeRecordUtils.subMessageCount(userId);

		try {
			sseEmitter.send(SseEmitter.event()
					.data("[DONE]"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		sseEmitter.complete();
	}


	@SneakyThrows
	@Override
	public void onFailure(EventSource eventSource, Throwable t, Response response) {
		if (Objects.isNull(response)) {
			return;
		}
		ResponseBody body = response.body();
		if (Objects.nonNull(body)) {
			log.error("Dify  sse连接异常data：{}，异常：{}", body.string(), t);
		} else {
			log.error("Dify  sse连接异常data：{}，异常：{}", response, t);
		}
		eventSource.cancel();
	}


	/**
	 * tokens
	 *
	 * @return
	 */
	public long tokens() {
		return tokens;
	}


}
