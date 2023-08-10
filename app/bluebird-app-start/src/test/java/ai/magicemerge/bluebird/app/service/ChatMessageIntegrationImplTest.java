package ai.magicemerge.bluebird.app.service;

import ai.magicemerge.bluebird.app.integration.ChatMessageIntegration;
import ai.magicemerge.bluebird.app.integration.dto.ChatMessageDto;
import ai.magicemerge.bluebird.app.integration.dto.ChatRequestDto;
import ai.magicemerge.bluebird.app.start.BluebirdAppApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BluebirdAppApplication.class)
public class ChatMessageIntegrationImplTest {

	@Resource
	private ChatMessageIntegration chatMessageIntegration;


	@Test
	public void getSSEMessage() {
		chatMessageIntegration.createSse(UUID.randomUUID().toString().replaceAll("-",""));
	}


	@Test
	public void sendSSEMessage() {
//		ChatRequestDto requestDto = new ChatRequestDto();
//		requestDto.setQuery("hello");
//		requestDto.setUser("mazean");
//		chatMessageIntegration.sseChat("2e672e48e93e48b9ab95edeff3eba52a",
//				requestDto);
	}


}
