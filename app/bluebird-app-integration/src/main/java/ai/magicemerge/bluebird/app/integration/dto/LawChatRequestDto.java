package ai.magicemerge.bluebird.app.integration.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LawChatRequestDto implements Serializable {
	private static final long serialVersionUID = 8209357245661643359L;


	private String prompt;

	private String response_mode;


}
