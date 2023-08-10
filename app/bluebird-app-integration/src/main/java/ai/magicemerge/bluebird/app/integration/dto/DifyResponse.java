package ai.magicemerge.bluebird.app.integration.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class DifyResponse<T> implements Serializable {
	private static final long serialVersionUID = -7030827739983856730L;

	private Long limit;

	private Boolean has_more;

	private String content;

	private T data;

}
