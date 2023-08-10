package ai.magicemerge.bluebird.app.service.dto;

import ai.magicemerge.bluebird.app.service.common.PageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class MemberDto extends PageEntity implements Serializable {

	private static final long serialVersionUID = 4310062363341604110L;

	private Long id;
	private Long workspaceId;
	private String workspaceName;
	private String username;
	private String userId;
	private String userType;
	private Boolean isActive;
	private Date createAt;

}
