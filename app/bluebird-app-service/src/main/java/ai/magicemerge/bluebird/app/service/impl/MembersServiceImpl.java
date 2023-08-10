package ai.magicemerge.bluebird.app.service.impl;

import ai.magicemerge.bluebird.app.dal.mapper.WorkspaceMapper;
import ai.magicemerge.bluebird.app.dal.model.Members;
import ai.magicemerge.bluebird.app.dal.mapper.MembersMapper;
import ai.magicemerge.bluebird.app.dal.model.User;
import ai.magicemerge.bluebird.app.dal.model.Workspace;
import ai.magicemerge.bluebird.app.service.MembersService;
import ai.magicemerge.bluebird.app.service.WorkspaceService;
import ai.magicemerge.bluebird.app.service.common.PageEntity;
import ai.magicemerge.bluebird.app.service.common.utils.CopyUtils;
import ai.magicemerge.bluebird.app.service.common.utils.PageUtils;
import ai.magicemerge.bluebird.app.service.common.utils.WebContextUtil;
import ai.magicemerge.bluebird.app.service.dto.MemberDto;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GygesM
 * @since 2023-06-10
 */
@Service
public class MembersServiceImpl extends ServiceImpl<MembersMapper, Members> implements MembersService {

	@Autowired
	private MembersMapper membersMapper;

	@Autowired
	private WorkspaceMapper workspaceMapper;


	@Override
	public List<Workspace> getWorkspaceList(String userId) {

		List<Workspace> resultList = Lists.newArrayList();

		if (StringUtils.isNotBlank(userId)) {
			LambdaQueryWrapper<Members> queryWrapper = new LambdaQueryWrapper<>();

			queryWrapper.eq(Members::getIsActive, true)
					.eq(Members::getIsDeleted, false)
					.eq(Members::getUserId, userId);

			List<Workspace> workspaceList = workspaceMapper.selectList(new LambdaQueryWrapper<Workspace>()
			.eq(Workspace::getIsDeleted, false).eq(Workspace::getIsValid, true).orderByAsc(Workspace::getId));

			Map<Long, Workspace> workspaceMap = workspaceList.stream()
					.collect(Collectors.toMap(Workspace::getId, workspace -> workspace));


			List<Members> members = membersMapper.selectList(queryWrapper);
			if (CollectionUtil.isNotEmpty(members)) {
				members.forEach(mem -> {
					if (workspaceMap.containsKey(mem.getWorkspaceId())) {
						resultList.add(workspaceMap.get(mem.getWorkspaceId()));
					}
				});
			}
		}
		return resultList;
	}

	@Override
	public PageEntity<MemberDto> getPageList(MemberDto memberDto) {

		Page<Members> membersPage = new Page<>(memberDto.getPage(), memberDto.getPageSize());

		LambdaQueryWrapper<Members> queryWrapper = new LambdaQueryWrapper<Members>()
				.eq(Members::getIsDeleted, false)
				.orderByDesc(Members::getCreateAt);

		if (StringUtils.isNotBlank(memberDto.getUsername())) {
			queryWrapper.like(Members::getUsername, memberDto.getUsername());
		}

		Page<Members> selectPage = baseMapper.selectPage(membersPage, queryWrapper);

		List<Workspace> workspaceList = workspaceMapper.selectList(new LambdaQueryWrapper<Workspace>()
				.eq(Workspace::getIsDeleted, false).eq(Workspace::getIsValid, true).orderByAsc(Workspace::getId));

		Map<Long, Workspace> workspaceMap = workspaceList.stream()
				.collect(Collectors.toMap(Workspace::getId, workspace -> workspace));


		List<MemberDto> memberDtos = selectPage.getRecords().stream()
				.map(members -> {
					MemberDto dto = CopyUtils.copy(members, MemberDto.class);
					if (workspaceMap.containsKey(dto.getWorkspaceId())) {
						dto.setWorkspaceName(workspaceMap.get(dto.getWorkspaceId()).getName());
					}
					return dto;
				}).collect(Collectors.toList());

		PageEntity<MemberDto> memberDtoPageEntity = PageUtils.listToPage(memberDtos, selectPage.getCurrent(), selectPage.getSize(), selectPage.getTotal());

		return memberDtoPageEntity;
	}

	@Override
	public boolean save(Members entity) {
		entity.setCreateBy(WebContextUtil.getUserSession().getUserId());
		return super.save(entity);
	}

	@Override
	public boolean updateById(Members entity) {
		entity.setUpdateBy(WebContextUtil.getUserSession().getUserId());
		return super.updateById(entity);
	}
}
