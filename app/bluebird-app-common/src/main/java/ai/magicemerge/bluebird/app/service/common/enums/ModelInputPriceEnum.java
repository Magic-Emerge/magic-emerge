package ai.magicemerge.bluebird.app.service.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ModelInputPriceEnum {

	GPT3_5("GPT3.5", "0.0015"),
	GPT3_5_16K("GPT3.5-16K", "0.003"),
	GPT4("GPT4", "0.03")
	;


	private final String model;

	private final String price;


}
