package ai.magicemerge.bluebird.app.web.controller;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ai.magicemerge.bluebird.app.service.common.constant.Const.BASE_API;

@Api(tags = "EmergeFlow聊天接口")
@RestController
@RequestMapping(value = BASE_API + "/flowise")
public class EmergeFlowChatRest {



}
