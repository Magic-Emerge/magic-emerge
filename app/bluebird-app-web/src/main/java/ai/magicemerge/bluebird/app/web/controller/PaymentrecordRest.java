package ai.magicemerge.bluebird.app.web.controller;

import ai.magicemerge.bluebird.app.service.common.ApiResponse;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ai.magicemerge.bluebird.app.service.PaymentrecordService;
import ai.magicemerge.bluebird.app.dal.model.PaymentRecord;
import org.springframework.beans.factory.annotation.Autowired;

import static ai.magicemerge.bluebird.app.service.common.constant.Const.BASE_API;

/**
 * <p>
 * 应用付费记录 前端控制器
 * </p>
 *
 * @author GygesM
 * @since 2023-05-29
 */
@Api(tags = "应用付费记录")
@RestController
@RequestMapping(BASE_API + "/payment-record")
public class PaymentrecordRest {


    @Autowired
    private PaymentrecordService paymentrecordService;

    @GetMapping(value = "/")
    public ApiResponse<Page<PaymentRecord>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<PaymentRecord> aPage = paymentrecordService.page(new Page<>(current, pageSize));
        return ApiResponse.ok(aPage);
    }

    @GetMapping(value = "/{id}")
    public ApiResponse<PaymentRecord> getById(@PathVariable("id") String id) {
        return ApiResponse.ok(paymentrecordService.getById(id));
    }

    @PostMapping(value = "/create")
    public ApiResponse<Object> create(@RequestBody PaymentRecord params) {
        paymentrecordService.save(params);
        return ApiResponse.ok("created successfully");
    }

}
