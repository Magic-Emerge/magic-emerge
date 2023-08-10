package ai.magicemerge.bluebird.app.web.controller;

import ai.magicemerge.bluebird.app.dal.model.Search;
import ai.magicemerge.bluebird.app.service.SearchService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static ai.magicemerge.bluebird.app.service.common.constant.Const.ADMIN_BASE_API;

/**
 * <p>
 *  法律搜索
 * </p>
 *
 * @author GygesM
 * @since 2023-06-07
 */
@Api(tags = "法律历史搜索页面")
@Controller
@RequestMapping(ADMIN_BASE_API + "/law-gpt/search")
public class SearchLawHistoryRest {


    @Autowired
    private SearchService searchService;


    @GetMapping(value = "/")
    public ResponseEntity<Page<Search>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<Search> aPage = searchService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Search> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(searchService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody Search params) {
        searchService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }


}
