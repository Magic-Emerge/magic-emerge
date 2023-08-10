package ai.magicemerge.bluebird.app.web.controller;

import ai.magicemerge.bluebird.app.service.common.ApiResponse;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ai.magicemerge.bluebird.app.service.SubscriberService;
import ai.magicemerge.bluebird.app.dal.model.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;

import static ai.magicemerge.bluebird.app.service.common.constant.Const.BASE_API;

/**
 * <p>
 * 会员信息 前端控制器
 * </p>
 *
 * @author GygesM
 * @since 2023-05-29
 */
@Api(tags = "会员订阅")
@RestController
@RequestMapping(BASE_API + "/subscriber")
public class SubscriberRest {


    @Autowired
    private SubscriberService subscriberService;

    @GetMapping(value = "/")
    public ApiResponse<Page<Subscriber>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<Subscriber> aPage = subscriberService.page(new Page<>(current, pageSize));
        return ApiResponse.ok(aPage);
    }

    @GetMapping(value = "/{id}")
    public ApiResponse<Subscriber> getById(@PathVariable("id") String id) {
        return ApiResponse.ok(subscriberService.getById(id));
    }

    @PostMapping(value = "/create")
    public ApiResponse<Object> create(@RequestBody Subscriber params) {
        subscriberService.save(params);
        return ApiResponse.ok("created successfully");
    }

    @PostMapping(value = "/update")
    public ApiResponse<Object> update(@RequestBody Subscriber params) {
        subscriberService.updateById(params);
        return ApiResponse.ok("updated successfully");
    }
}
