package ai.magicemerge.bluebird.app.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AppCategoriesDto implements Serializable {

	private static final long serialVersionUID = 853494254061847980L;

	private Integer type;

	private String name;

}
