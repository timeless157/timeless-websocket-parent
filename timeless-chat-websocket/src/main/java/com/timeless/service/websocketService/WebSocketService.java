package com.timeless.service.websocketService;

import com.timeless.constants.Constants;
import com.timeless.domain.entity.ChatUser;
import com.timeless.domain.entity.PrivateMessage;
import com.timeless.enums.AppHttpCodeEnum;
import com.timeless.exception.SystemException;
import com.timeless.service.PrivateMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;


/**
 * 功能描述：简单消息模板，用来推送消息
 */
@Service
public class WebSocketService {


    /**
    * @Description: SimpMessagingTemplate 是Spring框架提供的一个用于发送消息到WebSocket目标的模板类，
    *                   类似于redisTemplate , rabbitmqTemplate。
    * @Date: 2023/6/27 23:51
    * @Author: timeless
    */
    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private PrivateMessageService privateMessageService;

    /**
     * 简单点对点聊天室
     */
    public void sendChatMessage(PrivateMessage message) {
        message.setMessage(message.getFromUsername() + " 发送：" + message.getMessage());
        //可以看出template最大的灵活就是我们可以获取前端传来的参数来指定订阅地址
        //前面参数是订阅地址，后面参数是消息信息
        template.convertAndSend("/topic/ServerToClient.private." + message.getToUsername(), message);
        // 消息存储到数据库
        boolean save = privateMessageService.save(message);
        if(!save){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

}