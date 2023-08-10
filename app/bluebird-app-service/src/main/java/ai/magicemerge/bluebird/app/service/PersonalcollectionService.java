package ai.magicemerge.bluebird.app.service;

import ai.magicemerge.bluebird.app.dal.model.PersonalCollection;
import ai.magicemerge.bluebird.app.dal.model.Store;
import ai.magicemerge.bluebird.app.service.dto.AppStoreDto;
import ai.magicemerge.bluebird.app.service.dto.AppStoreStarDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 应用个人收藏 服务类
 * </p>
 *
 * @author GygesM
 * @since 2023-05-29
 */
public interface PersonalcollectionService extends IService<PersonalCollection> {

	Boolean starApp(Long appId);

	Boolean unstarApp(Long id);

	List<AppStoreStarDto> getCollectionApps(AppStoreDto appStoreDto);

	Map<Long, Store> getAppStoreMapping(List<Long> appIds);


}
