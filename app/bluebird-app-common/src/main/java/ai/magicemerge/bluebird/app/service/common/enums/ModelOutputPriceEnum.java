package ai.magicemerge.bluebird.app.service.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ModelOutputPriceEnum {

	GPT3_5("GPT3.5", "0.002"),
	GPT3_5_16K("GPT3.5-16K", "0.004"),
	GPT4("GPT4", "0.06");


	private final String model;

	private final String price;




}
