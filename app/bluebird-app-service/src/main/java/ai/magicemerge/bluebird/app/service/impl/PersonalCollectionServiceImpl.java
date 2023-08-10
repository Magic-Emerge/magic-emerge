package ai.magicemerge.bluebird.app.service.impl;

import ai.magicemerge.bluebird.app.dal.mapper.StoreMapper;
import ai.magicemerge.bluebird.app.dal.model.PersonalCollection;
import ai.magicemerge.bluebird.app.dal.mapper.PersonalCollectionMapper;
import ai.magicemerge.bluebird.app.dal.model.Store;
import ai.magicemerge.bluebird.app.dal.utils.UserUtils;
import ai.magicemerge.bluebird.app.service.PersonalcollectionService;
import ai.magicemerge.bluebird.app.service.StoreService;
import ai.magicemerge.bluebird.app.service.common.exceptions.BusinessException;
import ai.magicemerge.bluebird.app.service.common.utils.CopyUtils;
import ai.magicemerge.bluebird.app.service.common.utils.WebContextUtil;
import ai.magicemerge.bluebird.app.service.dto.AppStoreDto;
import ai.magicemerge.bluebird.app.service.dto.AppStoreStarDto;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 应用个人收藏 服务实现类
 * </p>
 *
 * @author GygesM
 * @since 2023-05-29
 */
@Service
public class PersonalCollectionServiceImpl extends ServiceImpl<PersonalCollectionMapper, PersonalCollection> implements PersonalcollectionService {

	@Autowired
	private StoreMapper storeMapper;

	@Override
	public Boolean starApp(Long appId) {

		Store byId = storeMapper.selectById(appId);
		if (Objects.isNull(byId)) {
			throw new BusinessException("this app is not exist");
		}

		Long workspaceId = WebContextUtil.getUserSession().getWorkspaceId();
		String userId = WebContextUtil.getUserSession().getUserId();

		Assert.notNull(workspaceId, "workspace id not null");
		Assert.notEmpty(userId, "user id not null");

		PersonalCollection collection = new PersonalCollection();
		collection.setWorkspaceId(workspaceId);
		collection.setCollectOfUserId(userId);
		collection.setAppName(byId.getAppName());
		collection.setAppId(byId.getId());
		collection.setAppType(byId.getAppType());
		int result = baseMapper.insert(collection);
		return result > 0;
	}

	@Override
	public Boolean unstarApp(Long id) {
		int deleteById = this.baseMapper.deleteById(id);
		return deleteById > 0;
	}

	@Override
	public List<AppStoreStarDto> getCollectionApps(AppStoreDto st) {

		Long workspaceId = WebContextUtil.getUserSession().getWorkspaceId();
		String userId = WebContextUtil.getUserSession().getUserId();

		Assert.notNull(workspaceId, "workspace id not null");
		Assert.notEmpty(userId, "user id not null");
		LambdaQueryWrapper<PersonalCollection> queryChainWrapper = new LambdaQueryWrapper<PersonalCollection>()
				.eq(PersonalCollection::getCollectOfUserId, userId)
				.eq(PersonalCollection::getWorkspaceId, workspaceId);

		if (StringUtils.isNotBlank(st.getAppName())) {
			queryChainWrapper.like(PersonalCollection::getAppName, st.getAppName());
		}

		if (Objects.nonNull(st.getAppType())) {
			queryChainWrapper.eq(PersonalCollection::getAppType, st.getAppType());
		}

		List<PersonalCollection> personalCollections = baseMapper.selectList(queryChainWrapper);
		List<Long> appids = personalCollections.stream().map(PersonalCollection::getAppId)
				.collect(Collectors.toList());


		if (CollectionUtil.isNotEmpty(appids)) {
			List<AppStoreStarDto> appStoreStarDtos = Lists.newArrayList();
			Map<Long, Store> appStoreMapping = this.getAppStoreMapping(appids);

			personalCollections.forEach(personalCollection ->  {
				Store item = appStoreMapping.get(personalCollection.getAppId());
				AppStoreStarDto appStoreStarDto = CopyUtils.copy(item, AppStoreStarDto.class);
				appStoreStarDto.setAppPersonalCollectionId(personalCollection.getId());
				appStoreStarDto.setIsFavorite(true);
				appStoreStarDtos.add(appStoreStarDto);
			});

			return appStoreStarDtos;
		}
		return Collections.emptyList();
	}

	@Override
	public Map<Long, Store> getAppStoreMapping(List<Long> appIds) {
		LambdaQueryWrapper<Store> queryWrapper = new LambdaQueryWrapper<Store>()
				.eq(Store::getIsDeleted, false)
				.in(Store::getId, appIds);
		List<Store> stores = storeMapper.selectList(queryWrapper);
		Map<Long, Store> storeMap = stores.stream()
				.collect(Collectors.toMap(Store::getId, k -> k));
		return storeMap;
	}
}
