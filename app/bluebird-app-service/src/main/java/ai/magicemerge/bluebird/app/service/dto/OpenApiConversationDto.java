package ai.magicemerge.bluebird.app.service.dto;

import ai.magicemerge.bluebird.app.integration.dto.ConversationsDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OpenApiConversationDto implements Serializable {

	private static final long serialVersionUID = 1796937217102393617L;


	private Long limit;

	private Boolean hasMore;

	private List<ConversationsDto> historyList;


}
