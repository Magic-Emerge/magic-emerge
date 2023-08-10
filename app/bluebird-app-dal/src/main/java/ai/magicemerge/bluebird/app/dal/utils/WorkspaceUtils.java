package ai.magicemerge.bluebird.app.dal.utils;

import ai.magicemerge.bluebird.app.dal.mapper.WorkspaceMapper;
import ai.magicemerge.bluebird.app.dal.model.Workspace;
import ai.magicemerge.bluebird.app.service.common.utils.SpringContextHolder;

public class WorkspaceUtils {

	private static final WorkspaceMapper workspaceMapper = SpringContextHolder.getBean(WorkspaceMapper.class);



	/**
	 * 保存信息
	 *
	 * @param workspaceId
	 * @return
	 */
	public static Workspace getWorkspace(Long workspaceId) {
		return workspaceMapper.selectById(workspaceId);
	}


}
