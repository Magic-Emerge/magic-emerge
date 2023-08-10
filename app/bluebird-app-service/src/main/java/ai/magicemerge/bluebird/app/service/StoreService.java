package ai.magicemerge.bluebird.app.service;

import ai.magicemerge.bluebird.app.dal.model.Category;
import ai.magicemerge.bluebird.app.dal.model.PersonalCollection;
import ai.magicemerge.bluebird.app.dal.model.Store;
import ai.magicemerge.bluebird.app.service.common.PageEntity;
import ai.magicemerge.bluebird.app.service.dto.AppCategoriesDto;
import ai.magicemerge.bluebird.app.service.dto.AppStoreDto;
import ai.magicemerge.bluebird.app.service.dto.AppStoreStarDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.security.PermissionCollection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 应用市场 服务类
 * </p>
 *
 * @author GygesM
 * @since 2023-05-29
 */
public interface StoreService extends IService<Store> {

	Boolean saveAppStore(AppStoreDto dto);

	PageEntity<AppStoreStarDto> getPage(AppStoreDto appStoreDto);

	Boolean deleteById(Long id);

	List<Category> getCategories();

	Map<Long, List<PersonalCollection>> getCollectionsMap();

	AppStoreDto getAppStoreByAppKey(String appKey);


}
