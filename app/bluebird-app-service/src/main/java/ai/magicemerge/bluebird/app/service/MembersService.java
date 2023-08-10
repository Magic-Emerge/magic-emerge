package ai.magicemerge.bluebird.app.service;

import ai.magicemerge.bluebird.app.dal.model.Members;
import ai.magicemerge.bluebird.app.dal.model.Workspace;
import ai.magicemerge.bluebird.app.service.common.PageEntity;
import ai.magicemerge.bluebird.app.service.dto.MemberDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GygesM
 * @since 2023-06-10
 */
public interface MembersService extends IService<Members> {

	List<Workspace> getWorkspaceList(String userId);

	PageEntity<MemberDto> getPageList(MemberDto memberDto);

}
