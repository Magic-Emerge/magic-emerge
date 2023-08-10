package ai.magicemerge.bluebird.app.dal.dto;

import lombok.Data;
import java.io.Serializable;


@Data
public class AnalysisQueryDto implements Serializable {
	private static final long serialVersionUID = -3412291420958536504L;


	private String workspaceId;

	private String openUserId;

	private String params;

}
