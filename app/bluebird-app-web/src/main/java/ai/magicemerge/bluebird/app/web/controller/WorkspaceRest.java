package ai.magicemerge.bluebird.app.web.controller;

import ai.magicemerge.bluebird.app.service.common.ApiResponse;
import ai.magicemerge.bluebird.app.service.common.annotions.JwtScope;
import ai.magicemerge.bluebird.app.service.dto.WorkspaceDto;
import cn.hutool.core.lang.Assert;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ai.magicemerge.bluebird.app.service.WorkspaceService;
import ai.magicemerge.bluebird.app.dal.model.Workspace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static ai.magicemerge.bluebird.app.service.common.constant.Const.BASE_API;

/**
 * <p>
 * 工作空间 前端控制器
 * </p>
 *
 * @author GygesM
 * @since 2023-05-29
 */
@Api(tags = "工作空间")
@RestController
@RequestMapping(BASE_API + "/workspace")
public class WorkspaceRest {


    @Autowired
    private WorkspaceService workspaceService;


    @JwtScope
    @PostMapping
    public ApiResponse<List<WorkspaceDto>> list(@RequestBody WorkspaceDto workspaceDto) {
        return ApiResponse.ok(workspaceService.workspaceList(workspaceDto));
    }

    @JwtScope
    @PostMapping(value = "/detail")
    public ApiResponse<Workspace> getById(@RequestBody WorkspaceDto workspaceDto) {
        Assert.notNull(workspaceDto.getId(), "workspace id not null");
        return ApiResponse.ok(workspaceService.getById(workspaceDto.getId()));
    }

    @JwtScope
    @PostMapping(value = "/create")
    public ApiResponse<Object> create(@RequestBody Workspace params) {
        workspaceService.save(params);
        return ApiResponse.ok();
    }

    @JwtScope
    @PostMapping(value = "/update")
    public ApiResponse<Object> update(@RequestBody Workspace params) {
        Assert.notNull(params.getId(), "workspace id not null");
        workspaceService.updateById(params);
        return ApiResponse.ok();
    }

    @JwtScope
    @PostMapping(value = "/is-owner")
    public ApiResponse<Object> isOwner() {
      return ApiResponse.ok(workspaceService.isWorkspaceOwner());
    }

}
