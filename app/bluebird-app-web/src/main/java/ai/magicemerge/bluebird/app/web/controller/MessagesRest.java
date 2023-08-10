package ai.magicemerge.bluebird.app.web.controller;

import ai.magicemerge.bluebird.app.integration.dto.MessageFeedbackDto;
import ai.magicemerge.bluebird.app.service.common.ApiResponse;
import ai.magicemerge.bluebird.app.service.common.annotions.JwtScope;
import ai.magicemerge.bluebird.app.service.common.utils.LocalAssert;
import ai.magicemerge.bluebird.app.service.dto.HistoryMessageDto;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ai.magicemerge.bluebird.app.service.MessagesService;
import ai.magicemerge.bluebird.app.dal.model.Messages;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static ai.magicemerge.bluebird.app.service.common.constant.Const.ADMIN_BASE_API;
import static ai.magicemerge.bluebird.app.service.common.constant.Const.BASE_API;

/**
 * <p>
 * AI聊天记录 前端控制器
 * </p>
 *
 * @author GygesM
 * @since 2023-06-04
 */
@Api(tags = "AI聊天记录")
@RestController
@RequestMapping(BASE_API + "/messages")
public class MessagesRest {


    @Autowired
    private MessagesService messagesService;


    @JwtScope
    @PostMapping(value = "/feedback")
    public ApiResponse<Boolean> setFeedback(@RequestBody MessageFeedbackDto messageFeedbackDto) {
        LocalAssert.isBlank(messageFeedbackDto.getMessageId(), "消息ID不能为空");
        Boolean aBoolean = messagesService.setFeedback(messageFeedbackDto);
        return aBoolean ? ApiResponse.ok() : ApiResponse.error("反馈失败");
    }


    @JwtScope
    @PostMapping(value = "/history")
    public ApiResponse<List<Messages>> getHistoryMessage(@RequestBody @Validated HistoryMessageDto historyMessageDto) {
        return ApiResponse.ok(messagesService.getHistoryMessages(historyMessageDto));
    }



    @JwtScope
    @PostMapping(value = "/latest")
    public ApiResponse<List<Messages>> getLatestMessages(@RequestBody @Validated HistoryMessageDto historyMessageDto) {
        return ApiResponse.ok(messagesService.getLatestMessageWithCurUser(historyMessageDto));
    }



    @JwtScope
    @PostMapping(value = "/update")
    public ApiResponse<Object> updateMessage(@RequestBody Messages messages) {
        return ApiResponse.ok(messagesService.updateById(messages));
    }

}
