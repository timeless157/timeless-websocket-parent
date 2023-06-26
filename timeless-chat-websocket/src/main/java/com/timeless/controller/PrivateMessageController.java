package com.timeless.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.timeless.domain.ResponseResult;
import com.timeless.domain.entity.Friend;
import com.timeless.domain.entity.PrivateMessage;
import com.timeless.enums.AppHttpCodeEnum;
import com.timeless.exception.SystemException;
import com.timeless.service.PrivateMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author timeless
 * @create 2023-06-24 18:24
 */
@RestController
@RequestMapping("/PrivateMessageController")
public class PrivateMessageController {

    @Autowired
    private PrivateMessageService privateMessageService;

    @Autowired
    private SimpMessagingTemplate template;

    /**
     * @Description: 添加一条消息
     * @Date: 2023/6/24 18:37
     * @Author: timeless
     */
    @PostMapping("/saveOneMsg")
    public void saveOneMsg(@RequestBody PrivateMessage privateMessage) {
        boolean save = privateMessageService.save(privateMessage);
        if (!save) {
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    /**
     * @Description: 查询私聊记录
     * @Date: 2023/6/24 18:40
     * @Author: timeless
     */
    @GetMapping("/listPrivateMessages/{fromUsername}/{toUsername}")
    public ResponseResult listPrivateMessages(
            @PathVariable("fromUsername") String fromUsername,
            @PathVariable("toUsername") String toUsername) {

        List<String> messages = privateMessageService.list(Wrappers
                        .<PrivateMessage>lambdaQuery()
                        .and(q -> q
                                .eq(PrivateMessage::getFromUsername, fromUsername)
                                .eq(PrivateMessage::getToUsername, toUsername)
                        )
                        .or(q -> q
                                .eq(PrivateMessage::getFromUsername, toUsername)
                                .eq(PrivateMessage::getToUsername, fromUsername)
                        ))
                .stream()
                .map(PrivateMessage::getMessage)
                .collect(Collectors.toList());

        return ResponseResult.okResult(messages);
    }

    /**
     * @Description: 删除所有聊天记录
     * @Date: 2023/6/24 20:27
     * @Author: timeless
     */
    @DeleteMapping("/deleteAllMsg/{fromUsername}/{toUsername}")
    public ResponseResult deleteAllMsg(
            @PathVariable("fromUsername") String fromUsername,
            @PathVariable("toUsername") String toUsername) {
        boolean remove = privateMessageService.remove((Wrappers
                .<PrivateMessage>lambdaQuery()
                .and(q -> q
                        .eq(PrivateMessage::getFromUsername, fromUsername)
                        .eq(PrivateMessage::getToUsername, toUsername)
                )
                .or(q -> q
                        .eq(PrivateMessage::getFromUsername, toUsername)
                        .eq(PrivateMessage::getToUsername, fromUsername)
                )));
        Friend friend = Friend.builder().fromUsername(fromUsername).toUsername(toUsername).build();
        template.convertAndSend("/topic/ServerToClient.deleteMsg", friend);
        return ResponseResult.okResult();
    }


}
