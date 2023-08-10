package ai.magicemerge.bluebird.app.service.common;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
public class UserToken implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userId;

	private String username;

	private Long workspaceId;

	private Boolean isAdmin;

	private LocalDateTime loginTime;

	private String userRole;

	private Boolean isWorkspaceOwner;

	private String openUserId;

	private List<String> permissions;

}
