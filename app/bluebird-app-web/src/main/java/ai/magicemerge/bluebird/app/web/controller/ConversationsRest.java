package ai.magicemerge.bluebird.app.web.controller;

import ai.magicemerge.bluebird.app.service.common.ApiResponse;
import ai.magicemerge.bluebird.app.service.common.annotions.JwtScope;
import ai.magicemerge.bluebird.app.service.common.utils.WebContextUtil;
import ai.magicemerge.bluebird.app.service.dto.ConversationDto;
import ai.magicemerge.bluebird.app.service.dto.ConversationHistoryDto;
import cn.hutool.core.lang.Assert;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ai.magicemerge.bluebird.app.service.ConversationsService;
import ai.magicemerge.bluebird.app.dal.model.Conversations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static ai.magicemerge.bluebird.app.service.common.constant.Const.BASE_API;

/**
 * <p>
 * 对话列表 前端控制器
 * </p>
 *
 * @author GygesM
 * @since 2023-07-10
 */
@Api(tags = "对话列表接口")
@RestController
@RequestMapping(BASE_API + "/conversations")
public class ConversationsRest {


	@Autowired
	private ConversationsService conversationsService;


	@JwtScope
	@PostMapping
	public ApiResponse<List<Conversations>> list(@RequestBody ConversationDto conversationDto) {
		List<Conversations> conversationsList = conversationsService.selectAllList(conversationDto);
		return ApiResponse.ok(conversationsList);
	}

	@JwtScope
	@PostMapping(value = "/detail")
	public ApiResponse<ConversationHistoryDto> getById(@RequestBody ConversationDto conversationDto) {
		Assert.notNull(conversationDto.getId(), "conversation id is not empty");
		return ApiResponse.ok(conversationsService.getConversationHistory(conversationDto));
	}

	@JwtScope
	@PostMapping(value = "/create")
	public ApiResponse<Object> create(@RequestBody Conversations params) {
		conversationsService.save(params);
		return ApiResponse.ok("created successfully");
	}

	@JwtScope
	@PostMapping(value = "/delete")
	public ApiResponse<Object> delete(@RequestBody ConversationDto conversationDto) {
		return ApiResponse.ok(conversationsService.deleteConversation(conversationDto));
	}

	@JwtScope
	@PostMapping(value = "/update")
	public ApiResponse<Object> update(@RequestBody Conversations params) {
		params.setUpdateBy(WebContextUtil.getUserSession().getUserId());
		conversationsService.updateById(params);
		return ApiResponse.ok("updated successfully");
	}
}
