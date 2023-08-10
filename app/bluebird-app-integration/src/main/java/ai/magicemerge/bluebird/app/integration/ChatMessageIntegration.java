package ai.magicemerge.bluebird.app.integration;

import ai.magicemerge.bluebird.app.integration.dto.ChatRequestDto;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface ChatMessageIntegration {

	/**
	 * 创建SSE
	 * @param uid
	 * @return
	 */
	SseEmitter createSse(String uid);

	/**
	 * 关闭SSE
	 * @param uid
	 */
	void closeSse(String uid);

	/**
	 * 客户端发送消息到服务端
	 * @param uid
	 * @param chatRequest
	 */
	void sseChat(String uid, ChatRequestDto chatRequest, Long workspaceId, String userId);
}
