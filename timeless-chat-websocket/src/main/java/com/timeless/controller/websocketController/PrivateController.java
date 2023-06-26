package com.timeless.controller.websocketController;

import com.timeless.domain.entity.PrivateMessage;
import com.timeless.service.websocketService.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;


/**
* @Description: 私聊
* @Date: 2023/6/24 17:47
* @Author: timeless
*/
@Controller
public class PrivateController {

    @Autowired
    private WebSocketService ws;

    @MessageMapping("/privateChat")
    public void singleChat(PrivateMessage message) {
        ws.sendChatMessage(message);
    }

}