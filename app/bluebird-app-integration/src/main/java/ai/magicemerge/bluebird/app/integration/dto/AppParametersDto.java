package ai.magicemerge.bluebird.app.integration.dto;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.io.Serializable;

@Data
public class AppParametersDto implements Serializable {
	private static final long serialVersionUID = 4703342626663493308L;


	private String opening_statement;

	private JSONArray suggested_questions;

	private State suggested_questions_after_answer;

	private State more_like_this;

	private JSONArray user_input_form;


	@Data
	public static class State {

		private Boolean enabled;
	}

}
