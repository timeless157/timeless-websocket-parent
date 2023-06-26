package com.timeless.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    public static Integer userNumber = 0;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        userNumber++;
        messagingTemplate.convertAndSend("/topic/ServerToClient.showUserNumber", userNumber);
        System.out.println("我来了哦~");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        userNumber--;
        messagingTemplate.convertAndSend("/topic/ServerToClient.showUserNumber", userNumber);
        System.out.println("我走了哦~");
    }
}
