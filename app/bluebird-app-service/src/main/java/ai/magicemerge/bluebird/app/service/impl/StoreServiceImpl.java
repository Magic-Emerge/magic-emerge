package ai.magicemerge.bluebird.app.service.impl;

import ai.magicemerge.bluebird.app.dal.mapper.CategoryMapper;
import ai.magicemerge.bluebird.app.dal.mapper.PersonalCollectionMapper;
import ai.magicemerge.bluebird.app.dal.mapper.UserMapper;
import ai.magicemerge.bluebird.app.dal.model.Category;
import ai.magicemerge.bluebird.app.dal.model.PersonalCollection;
import ai.magicemerge.bluebird.app.dal.model.Store;
import ai.magicemerge.bluebird.app.dal.mapper.StoreMapper;
import ai.magicemerge.bluebird.app.dal.utils.UserUtils;
import ai.magicemerge.bluebird.app.service.StoreService;
import ai.magicemerge.bluebird.app.service.UserService;
import ai.magicemerge.bluebird.app.service.common.PageEntity;
import ai.magicemerge.bluebird.app.service.common.enums.AppStateEnum;
import ai.magicemerge.bluebird.app.service.common.enums.SysUserRoleEnum;
import ai.magicemerge.bluebird.app.service.common.exceptions.BusinessException;
import ai.magicemerge.bluebird.app.service.common.utils.*;
import ai.magicemerge.bluebird.app.service.dto.AppCategoriesDto;
import ai.magicemerge.bluebird.app.service.dto.AppStoreDto;
import ai.magicemerge.bluebird.app.service.dto.AppStoreStarDto;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.security.PermissionCollection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 应用市场 服务实现类
 * </p>
 *
 * @author GygesM
 * @since 2023-05-29
 */
@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements StoreService {

	@Autowired
	private PersonalCollectionMapper personalCollectionMapper;

	@Autowired
	private CategoryMapper categoryMapper;

	@Override
	public Boolean saveAppStore(AppStoreDto dto) {
		Store entity = CopyUtils.copy(dto, Store.class);

		Long aLong = baseMapper.selectCount(new LambdaQueryWrapper<Store>()
				.eq(Store::getIsDeleted, false)
				.eq(Store::getAppKey, dto.getAppKey()));

		if (aLong > 0) {
			throw new BusinessException("app is exist");
		}

		String sig = String.valueOf(SnowFlakeUtils.getNextId());
		entity.setSignId(sig);
		if (!StringUtils.contains(dto.getAppVersion(), "v")) {
			entity.setAppVersion(String.format("v%s", dto.getAppVersion()));
		}
		String userId = WebContextUtil.getUserSession().getUserId();
		Map<String, String> userIdNameMap = UserUtils.userIdNameMap();
		if (userIdNameMap.containsKey(userId)) {
			entity.setAuthor(userIdNameMap.get(userId));
		}
		entity.setCreateBy(userId);
		entity.setWorkspaceId(WebContextUtil.getUserSession().getWorkspaceId());
		return super.save(entity);
	}


	@Override
	public PageEntity<AppStoreStarDto> getPage(AppStoreDto appStoreDto) {
		Page<Store> storePage = new Page<>(appStoreDto.getPage(), appStoreDto.getPageSize());

		Long workspaceId = WebContextUtil.getUserSession().getWorkspaceId();

		LambdaQueryWrapper<Store> queryWrapper = new LambdaQueryWrapper<Store>()
				.eq(Store::getWorkspaceId, workspaceId)
				.orderByDesc(Store::getCreateAt);

		if (AuthUtils.isUser()) {
			queryWrapper.eq(Store::getAppState, AppStateEnum.ONLINE.getCode());
		}

		if (StringUtils.isNotBlank(appStoreDto.getAppName())) {
			queryWrapper.like(Store::getAppName, appStoreDto.getAppName());
		}

		if (Objects.nonNull(appStoreDto.getAppType())) {
			queryWrapper.eq(Store::getAppType, appStoreDto.getAppType());
		}

		Page<Store> selectPage = baseMapper.selectPage(storePage, queryWrapper);
		Map<Long, List<PersonalCollection>> collectMap = this.getCollectionsMap();

		List<AppStoreStarDto> records = CopyUtils.copyList(selectPage.getRecords(), AppStoreStarDto.class);

		records.forEach(appStoreStarDto -> {
			if (collectMap.containsKey(appStoreStarDto.getId())) {
				appStoreStarDto.setIsFavorite(true);
				List<PersonalCollection> personalCollections = collectMap.get(appStoreStarDto.getId());
				appStoreStarDto.setAppPersonalCollectionId(personalCollections.get(0).getId());
			} else {
				appStoreStarDto.setIsFavorite(false);
			}
		});

		PageEntity<AppStoreStarDto> storePageEntity = PageUtils.listToPage(records,
				selectPage.getCurrent(), selectPage.getSize(), selectPage.getTotal());

		return storePageEntity;
	}

	@Override
	public Boolean deleteById(Long id) {
		return baseMapper.deleteById(id) > 0;
	}


	@Override
	public List<Category> getCategories() {
		LambdaQueryWrapper<Category> lambdaQuery = new LambdaQueryWrapper<Category>()
				.eq(Category::getIsValid, true);
		lambdaQuery.orderByAsc(Category::getId);
		return categoryMapper.selectList(lambdaQuery);
	}


	@Override
	public Map<Long, List<PersonalCollection>> getCollectionsMap() {
		Long workspaceId = WebContextUtil.getUserSession().getWorkspaceId();
		String userId = WebContextUtil.getUserSession().getUserId();

		LambdaQueryWrapper<PersonalCollection> collectionLambdaQueryWrapper = new LambdaQueryWrapper<PersonalCollection>()
				.eq(PersonalCollection::getIsDeleted, false)
				.eq(PersonalCollection::getCollectOfUserId, userId)
				.eq(PersonalCollection::getWorkspaceId, workspaceId);

		List<PersonalCollection> personalCollections = personalCollectionMapper.selectList(collectionLambdaQueryWrapper);
		Map<Long, List<PersonalCollection>> collectionMap = personalCollections.stream()
				.collect(Collectors.groupingBy(PersonalCollection::getAppId));

		return collectionMap;
	}

	@Override
	public AppStoreDto getAppStoreByAppKey(String appKey) {
		Store store = baseMapper.selectOne(new LambdaQueryWrapper<Store>()
				.eq(Store::getAppKey, appKey));
		AppStoreStarDto dto = CopyUtils.copy(store, AppStoreStarDto.class);
		return dto;
	}


}
