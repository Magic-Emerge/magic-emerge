package ai.magicemerge.bluebird.app.service.impl;

import ai.magicemerge.bluebird.app.dal.model.Workspace;
import ai.magicemerge.bluebird.app.dal.mapper.WorkspaceMapper;
import ai.magicemerge.bluebird.app.dal.utils.UserUtils;
import ai.magicemerge.bluebird.app.dal.utils.WorkspaceUtils;
import ai.magicemerge.bluebird.app.service.WorkspaceService;
import ai.magicemerge.bluebird.app.service.common.PageEntity;
import ai.magicemerge.bluebird.app.service.common.utils.CopyUtils;
import ai.magicemerge.bluebird.app.service.common.utils.WebContextUtil;
import ai.magicemerge.bluebird.app.service.dto.WorkspaceDto;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 工作空间 服务实现类
 * </p>
 *
 * @author GygesM
 * @since 2023-05-29
 */
@Service
public class WorkspaceServiceImpl extends ServiceImpl<WorkspaceMapper, Workspace> implements WorkspaceService {


	@Override
	public List<WorkspaceDto> workspaceList(WorkspaceDto workspaceDto) {

		LambdaQueryWrapper<Workspace> queryWrapper = new LambdaQueryWrapper<Workspace>()
				.eq(Workspace::getIsDeleted, false)
				.orderByDesc(Workspace::getCreateAt);

		if (StringUtils.isNotBlank(workspaceDto.getName())) {
			queryWrapper.like(Workspace::getName, workspaceDto.getName());
		}

		if (Objects.nonNull(workspaceDto.getIsValid())) {
			queryWrapper.eq(Workspace::getIsValid, workspaceDto.getIsValid());
		}


		Map<String, String> map = UserUtils.userIdNameMap();

		List<WorkspaceDto> workspaceList = baseMapper.selectList(queryWrapper)
				.stream().map(workspace -> {
					WorkspaceDto dto = CopyUtils.copy(workspace, WorkspaceDto.class);
					if (map.containsKey(workspace.getManagerId())) {
						String username = map.get(workspace.getManagerId());
						dto.setManagerName(username);
					}
					return dto;
				}).collect(Collectors.toList());

		return workspaceList;
	}

	@Override
	public Boolean isWorkspaceOwner() {
		Long workspaceId = WebContextUtil.getUserSession().getWorkspaceId();
		String userId = WebContextUtil.getUserSession().getUserId();
		Workspace workspace = WorkspaceUtils.getWorkspace(workspaceId);
		return userId.equals(workspace.getManagerId());
	}
}
