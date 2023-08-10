package ai.magicemerge.bluebird.app.web.controller;

import ai.magicemerge.bluebird.app.service.common.ApiResponse;
import ai.magicemerge.bluebird.app.service.common.annotions.JwtScope;
import ai.magicemerge.bluebird.app.service.dto.NotifyDto;
import cn.hutool.core.lang.Assert;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ai.magicemerge.bluebird.app.service.NotifyService;
import ai.magicemerge.bluebird.app.dal.model.Notify;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

import static ai.magicemerge.bluebird.app.service.common.constant.Const.BASE_API;

/**
 * <p>
 * 应用通知 前端控制器
 * </p>
 *
 * @author GygesM
 * @since 2023-07-09
 */
@Api(tags = "应用告警")
@RestController
@RequestMapping(BASE_API + "/notify")
public class NotifyRest {


    @Autowired
    private NotifyService notifyService;


    @JwtScope
    @PostMapping(value = "/list")
    public ApiResponse<List<Notify>> getNotifyList(@RequestBody NotifyDto notifyDto) {
        return ApiResponse.ok(notifyService.getNotifyList(notifyDto));
    }


    @JwtScope
    @PostMapping(value = "/detail")
    public ApiResponse<Notify> getNotifyById(@RequestBody NotifyDto notifyDto) {
        Assert.notNull(notifyDto.getId(), "notify id not null");
        return ApiResponse.ok(notifyService.getById(notifyDto.getId()));
    }


    @JwtScope
    @PostMapping(value = "/update")
    public ApiResponse<Object> update(@RequestBody Notify params) {
        if (Objects.isNull(params.getId())) {
            notifyService.save(params);
        } else {
            notifyService.updateById(params);
        }
        return ApiResponse.ok("updated successfully");
    }
}
