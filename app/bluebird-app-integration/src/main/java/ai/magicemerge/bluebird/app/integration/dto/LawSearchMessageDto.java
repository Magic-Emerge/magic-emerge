package ai.magicemerge.bluebird.app.integration.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LawSearchMessageDto implements Serializable {
	private static final long serialVersionUID = -3086354485919091710L;


	private String id;

	private String response;

}
