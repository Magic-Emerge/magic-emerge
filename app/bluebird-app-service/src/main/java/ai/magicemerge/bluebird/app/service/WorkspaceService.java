package ai.magicemerge.bluebird.app.service;

import ai.magicemerge.bluebird.app.dal.model.Workspace;
import ai.magicemerge.bluebird.app.service.dto.WorkspaceDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 工作空间 服务类
 * </p>
 *
 * @author GygesM
 * @since 2023-05-29
 */
public interface WorkspaceService extends IService<Workspace> {

	List<WorkspaceDto> workspaceList(WorkspaceDto workspaceDto);

	Boolean isWorkspaceOwner();

}
