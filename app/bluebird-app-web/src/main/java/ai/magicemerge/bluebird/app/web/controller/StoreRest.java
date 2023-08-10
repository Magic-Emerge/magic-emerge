package ai.magicemerge.bluebird.app.web.controller;

import ai.magicemerge.bluebird.app.dal.model.Category;
import ai.magicemerge.bluebird.app.service.PersonalcollectionService;
import ai.magicemerge.bluebird.app.service.common.ApiResponse;
import ai.magicemerge.bluebird.app.service.common.PageEntity;
import ai.magicemerge.bluebird.app.service.common.annotions.JwtScope;
import ai.magicemerge.bluebird.app.service.common.utils.CopyUtils;
import ai.magicemerge.bluebird.app.service.common.utils.WebContextUtil;
import ai.magicemerge.bluebird.app.service.dto.AppStoreDto;
import ai.magicemerge.bluebird.app.service.dto.AppStoreStarDto;
import cn.hutool.core.lang.Assert;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import ai.magicemerge.bluebird.app.service.StoreService;
import ai.magicemerge.bluebird.app.dal.model.Store;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static ai.magicemerge.bluebird.app.service.common.constant.Const.BASE_API;

/**
 * <p>
 * 应用市场 前端控制器
 * </p>
 *
 * @author GygesM
 * @since 2023-05-29
 */
@Api(tags = "应用商店")
@RestController
@RequestMapping(BASE_API + "/store")
public class StoreRest {


    @Autowired
    private StoreService storeService;

    @Autowired
    private PersonalcollectionService personalcollectionService;

    @JwtScope
    @PostMapping
    public ApiResponse<PageEntity<AppStoreStarDto>> list(@RequestBody AppStoreDto appStoreDto) {
        if (appStoreDto.getPage() == null) {
           appStoreDto.setPage(1L);
        }
        if (appStoreDto.getPageSize() == null) {
           appStoreDto.setPageSize(10L);
        }
        PageEntity<AppStoreStarDto> page = storeService.getPage(appStoreDto);
        return ApiResponse.ok(page);
    }

    @JwtScope
    @PostMapping(value = "/detail")
    public ApiResponse<Store> getById(@RequestBody AppStoreDto appStoreDto) {
        Assert.notNull(appStoreDto.getId(), "failed to get detail of app");
        return ApiResponse.ok(storeService.getById(appStoreDto.getId()));
    }

    @JwtScope
    @PostMapping(value = "/create")
    public ApiResponse<Object> create(@RequestBody AppStoreDto appStoreDto) {
        storeService.saveAppStore(appStoreDto);
        return ApiResponse.ok("created successfully");
    }

    @JwtScope
    @PostMapping(value = "/delete")
    public ApiResponse<Object> delete(@RequestBody AppStoreDto appStoreDto) {
        Assert.notNull(appStoreDto.getId(), "failed to get detail of app");
        Store removeStore = new Store();
        removeStore.setId(appStoreDto.getId());
        removeStore.setUpdateBy(WebContextUtil.getUserSession().getUserId());
        storeService.removeById(appStoreDto.getId());
        return ApiResponse.ok("deleted successfully");
    }

    @JwtScope
    @PostMapping(value = "/update")
    public ApiResponse<Object> update(@RequestBody Store store) {
        Assert.notNull(store.getId(), "failed to get detail of app");
        if (!StringUtils.contains(store.getAppVersion(), "v")) {
            store.setAppVersion(String.format("v%s", store.getAppVersion()));
        }
        store.setUpdateBy(WebContextUtil.getUserSession().getUserId());
        storeService.updateById(store);
        return ApiResponse.ok("updated successfully");
    }


    @JwtScope
    @PostMapping(value = "/categories")
    public ApiResponse<List<Category>> getCategories() {
        return ApiResponse.ok(storeService.getCategories());
    }

    @JwtScope
    @PostMapping(value = "/star")
    public ApiResponse<Object> starApp(@RequestBody AppStoreDto appStoreDto) {
        personalcollectionService.starApp(appStoreDto.getId());
        return ApiResponse.ok("updated successfully");
    }

    @JwtScope
    @PostMapping(value = "/unstar")
    public ApiResponse<Object> unStarApp(@RequestBody AppStoreStarDto appStoreDto) {
        personalcollectionService.unstarApp(appStoreDto.getAppPersonalCollectionId());
        return ApiResponse.ok("updated successfully");
    }



    @JwtScope
    @PostMapping(value = "/collections")
    public ApiResponse<List<AppStoreStarDto>> getAppPersonalCollectionList(@RequestBody AppStoreDto appStoreDto) {
        List<AppStoreStarDto> collectionApps = personalcollectionService.getCollectionApps(appStoreDto);
        return ApiResponse.ok(collectionApps);
    }


}
