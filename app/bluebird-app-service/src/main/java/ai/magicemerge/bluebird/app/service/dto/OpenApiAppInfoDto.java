package ai.magicemerge.bluebird.app.service.dto;

import ai.magicemerge.bluebird.app.integration.dto.AppParametersDto;
import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.io.Serializable;

@Data
public class OpenApiAppInfoDto implements Serializable {
	private static final long serialVersionUID = -686602270388410755L;



	private String openingStatement;

	private JSONArray suggestedQuestions;

	private OpenApiAppInfoDto.State suggestedQuestionsAfterAnswer;

	private OpenApiAppInfoDto.State moreLikeThis;

	private JSONArray userInputForm;


	@Data
	public static class State {

		private Boolean enabled;
	}


}
