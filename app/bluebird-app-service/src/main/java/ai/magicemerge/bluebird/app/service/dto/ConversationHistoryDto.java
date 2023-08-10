package ai.magicemerge.bluebird.app.service.dto;

import ai.magicemerge.bluebird.app.dal.model.Messages;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

@Data
public class ConversationHistoryDto implements Serializable {
	private static final long serialVersionUID = 9113149546010887707L;

	private ConversationDto conversationDto;

	private List<Messages> messages = Lists.newArrayList();

}
