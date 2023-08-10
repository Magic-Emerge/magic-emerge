package ai.magicemerge.bluebird.app.web.controller;

import ai.magicemerge.bluebird.app.dal.model.News;
import ai.magicemerge.bluebird.app.service.NewsService;
import ai.magicemerge.bluebird.app.service.common.ApiResponse;
import ai.magicemerge.bluebird.app.service.common.annotions.JwtScope;
import ai.magicemerge.bluebird.app.service.dto.AppNewsDto;
import ai.magicemerge.bluebird.app.web.params.AppNewsRequest;
import cn.hutool.core.lang.Assert;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static ai.magicemerge.bluebird.app.service.common.constant.Const.BASE_API;

@Api(tags = "应用咨询")
@RestController
@RequestMapping(BASE_API + "/news")
public class AppNewsRest {


	@Autowired
	private NewsService newsService;


	@JwtScope
	@PostMapping
	public ApiResponse<List<News>> list(@RequestBody AppNewsDto appNewsDto) {
		return ApiResponse.ok(newsService.getNews(appNewsDto));
	}



	@JwtScope
	@PostMapping("/detail")
	public ApiResponse<News> getDetail(@RequestBody AppNewsDto appNewsDto) {
		Assert.notNull(appNewsDto.getId(), "failed to get news");
		return ApiResponse.ok(newsService.getById(appNewsDto.getId()));
	}



	@JwtScope
	@PostMapping(value = "/create")
	public ApiResponse<Object> create(@RequestBody News params) {
		return ApiResponse.ok(newsService.save(params));
	}


	@JwtScope
	@PostMapping(value = "/update")
	public ApiResponse<Object> update(@RequestBody News params) {
		return ApiResponse.ok(newsService.updateById(params));
	}



}
