package ai.magicemerge.bluebird.app.web.controller;

import ai.magicemerge.bluebird.app.dal.dto.ActiveUserDto;
import ai.magicemerge.bluebird.app.dal.dto.AnalysisQueryDto;
import ai.magicemerge.bluebird.app.dal.dto.MessageCountDto;
import ai.magicemerge.bluebird.app.dal.dto.MessageTokenDto;
import ai.magicemerge.bluebird.app.service.AnalysisService;
import ai.magicemerge.bluebird.app.service.common.ApiResponse;
import ai.magicemerge.bluebird.app.service.common.annotions.JwtScope;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static ai.magicemerge.bluebird.app.service.common.constant.Const.BASE_API;

@Api(tags = "分析")
@RestController
@RequestMapping(BASE_API + "/analysis")
public class AnalysisRest {


	@Autowired
	private AnalysisService analysisService;

	//全部消息数
	//耗费token数
	//按用户级别过滤
	//按天进行过滤
	@JwtScope
	@PostMapping("/message-count")
	public ApiResponse<List<MessageCountDto>> queryMessageCountList(@RequestBody AnalysisQueryDto analysisQueryDto) {
		return ApiResponse.ok(analysisService.queryMessageCountList(analysisQueryDto));
	}


	@JwtScope
	@PostMapping("/message-token")
	public ApiResponse<List<MessageTokenDto>> queryMessageTokenList(@RequestBody AnalysisQueryDto analysisQueryDto) {
		return ApiResponse.ok(analysisService.queryMessageTokenList(analysisQueryDto));
	}


	@JwtScope
	@PostMapping("/user-count")
	public ApiResponse<List<ActiveUserDto>> queryActiveUserList(@RequestBody AnalysisQueryDto analysisQueryDto) {
		return ApiResponse.ok(analysisService.queryActiveUserList(analysisQueryDto));
	}




}
