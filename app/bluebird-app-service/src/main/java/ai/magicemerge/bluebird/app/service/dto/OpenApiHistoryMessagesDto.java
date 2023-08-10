package ai.magicemerge.bluebird.app.service.dto;

import ai.magicemerge.bluebird.app.integration.dto.HistoryMessageDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OpenApiHistoryMessagesDto implements Serializable {
	private static final long serialVersionUID = 169766153431657330L;


	private Long limit;

	private Boolean hasMore;

	private List<HistoryMessageDto> historyList;


}
