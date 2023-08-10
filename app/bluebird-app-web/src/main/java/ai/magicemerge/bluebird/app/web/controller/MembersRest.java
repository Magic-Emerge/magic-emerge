package ai.magicemerge.bluebird.app.web.controller;

import ai.magicemerge.bluebird.app.service.common.ApiResponse;
import ai.magicemerge.bluebird.app.service.common.PageEntity;
import ai.magicemerge.bluebird.app.service.dto.MemberDto;
import cn.hutool.core.lang.Assert;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ai.magicemerge.bluebird.app.service.MembersService;
import ai.magicemerge.bluebird.app.dal.model.Members;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

import static ai.magicemerge.bluebird.app.service.common.constant.Const.BASE_API;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author GygesM
 * @since 2023-06-10
 */

@Api(tags = "工作空间成员")
@RestController
@RequestMapping(BASE_API + "/members")
public class MembersRest {


	@Autowired
	private MembersService membersService;

	@PostMapping
	public ApiResponse<PageEntity<MemberDto>> list(@RequestBody MemberDto memberDto) {
		if (Objects.isNull(memberDto.getPage())) {
			memberDto.setPage(1L);
		}
		if (Objects.isNull(memberDto.getPageSize())) {
			memberDto.setPageSize(10L);
		}
		PageEntity<MemberDto> pageList = membersService.getPageList(memberDto);
		return ApiResponse.ok(pageList);
	}

	@PostMapping(value = "/detail")
	public ApiResponse<Members> getById(@RequestBody MemberDto memberDto) {
		Assert.notNull(memberDto.getId());
		return ApiResponse.ok(membersService.getById(memberDto.getId()));
	}


	@PostMapping(value = "/create")
	public ApiResponse<Object> create(@RequestBody Members params) {
		membersService.save(params);
		return ApiResponse.ok();
	}


	@PostMapping(value = "/update")
	public ApiResponse<Object> update(@RequestBody Members params) {
		membersService.updateById(params);
		return ApiResponse.ok();
	}

	@PostMapping(value = "/delete")
	public ApiResponse<Object> delete(@RequestBody Members params) {
		membersService.removeById(params);
		return ApiResponse.ok();
	}
}
