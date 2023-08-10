package ai.magicemerge.bluebird.app.web.controller;

import ai.magicemerge.bluebird.app.dal.model.SubRecord;
import ai.magicemerge.bluebird.app.service.SubRecordService;
import ai.magicemerge.bluebird.app.service.common.ApiResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static ai.magicemerge.bluebird.app.service.common.constant.Const.BASE_API;

/**
 * <p>
 * 应用操作记录 前端控制器
 * </p>
 *
 * @author GygesM
 * @since 2023-05-29
 */
@Api(tags = "应用操作记录")
@RestController
@RequestMapping(BASE_API + "/operate-record")
public class SubRecordRest {


    @Autowired
    private SubRecordService subRecordService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Page<SubRecord>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<SubRecord> aPage = subRecordService.page(new Page<>(current, pageSize));
        return ApiResponse.ok(aPage);
    }

    @GetMapping(value = "/{id}")
    public ApiResponse<SubRecord> getById(@PathVariable("id") String id) {
        return ApiResponse.ok(subRecordService.getById(id));
    }

    @PostMapping(value = "/create")
    public ApiResponse<Object> create(@RequestBody SubRecord params) {
        subRecordService.save(params);
        return ApiResponse.ok("created successfully");
    }

}
