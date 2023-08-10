package ai.magicemerge.bluebird.app.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChargeRecordDto implements Serializable {
	private static final long serialVersionUID = -4496119053865906870L;


	private Long id;

	private String userId;
}
