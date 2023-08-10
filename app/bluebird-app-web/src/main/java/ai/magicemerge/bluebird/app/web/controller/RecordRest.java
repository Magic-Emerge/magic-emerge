package ai.magicemerge.bluebird.app.web.controller;

import ai.magicemerge.bluebird.app.dal.model.Record;
import ai.magicemerge.bluebird.app.service.common.ApiResponse;
import ai.magicemerge.bluebird.app.service.dto.ChargeRecordDto;
import cn.hutool.core.lang.Assert;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ai.magicemerge.bluebird.app.service.RecordService;

import org.springframework.beans.factory.annotation.Autowired;

import static ai.magicemerge.bluebird.app.service.common.constant.Const.BASE_API;

/**
 * <p>
 * 充值记录 前端控制器
 * </p>
 *
 * @author GygesM
 * @since 2023-05-29
 */
@Api(tags = "充值记录")
@RestController
@RequestMapping(BASE_API + "/record")
public class RecordRest {

    @Autowired
    private RecordService recordService;

    @GetMapping(value = "/")
    public ApiResponse<Page<Record>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<Record> aPage = recordService.page(new Page<>(current, pageSize));
        return ApiResponse.ok(aPage);
    }

    @PostMapping(value = "/check-limit")
    public ApiResponse<Object> checkLimit(@RequestBody ChargeRecordDto chargeRecordDto) {
        Assert.notBlank(chargeRecordDto.getUserId(), "user id is not null");
        return ApiResponse.ok(recordService.checkLimit(chargeRecordDto.getUserId()));
    }

    @PostMapping(value = "/create")
    public ApiResponse<Object> create(@RequestBody Record params) {
        recordService.save(params);
        return ApiResponse.ok("created successfully");
    }

}
