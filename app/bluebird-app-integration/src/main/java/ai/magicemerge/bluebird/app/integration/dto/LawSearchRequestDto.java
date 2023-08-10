package ai.magicemerge.bluebird.app.integration.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LawSearchRequestDto implements Serializable {
	private static final long serialVersionUID = 2390402478498467449L;

	private String prompt;

	private Integer top;
}
