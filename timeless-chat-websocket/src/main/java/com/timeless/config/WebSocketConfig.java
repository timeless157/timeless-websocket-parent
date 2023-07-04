package com.timeless.config;

import com.timeless.constants.Constants;
import com.timeless.constants.RabbitMQProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author timeless
 * @create 2023-06-24 2:32
 * @Description: 注解用于开启使用STOMP协议来传输基于代理（MessageBroker）的消息，这时候控制器（controller）
 * 开始支持@MessageMapping,就像是使用@requestMapping一样。
 *
 * Stomp（Simple Text Oriented Messaging Protocol）是一种基于文本的简单消息传递协议，
 * 常用于在客户端和消息代理之间进行实时通信。Stomp协议可以在多种传输协议上运行，
 * 包括WebSocket。WebSocket是一种在单个TCP连接上提供全双工通信的协议，
 * 它可以在浏览器和服务器之间建立持久连接，适用于实时通信和推送消息。
 * 因此，Stomp可以在WebSocket上运行，提供实时消息传递的功能。
 *
 */
@Configuration
@Slf4j
@EnableWebSocketMessageBroker
@EnableConfigurationProperties(RabbitMQProperties.class)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private RabbitMQProperties rabbitMQProperties;

    public WebSocketConfig(RabbitMQProperties rabbitMQProperties){
        this.rabbitMQProperties = rabbitMQProperties;
        log.error(rabbitMQProperties.getHost());
    }

    /**
     * 注册端点，发布或者订阅消息的时候需要连接此端点
     * setAllowedOrigins 非必须，*表示允许其他域进行连接
     * withSockJS  表示开始sockejs支持
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(Constants.ENDPOINT).setAllowedOriginPatterns(Constants.ALLOWED_ORIGINS).withSockJS();
    }

    /**
     * 配置消息代理(中介)
     * enableSimpleBroker 服务端推送给客户端的路径前缀
     * setApplicationDestinationPrefixes  客户端发送数据给服务器端的一个前缀
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 基于内存的消息代理
        registry.enableSimpleBroker(Constants.SIMPLE_BROKER);
        registry.setApplicationDestinationPrefixes(Constants.DESTINATION_PREFIXES);

        // Use this for enabling a Full featured broker like RabbitMQ
//        registry.enableStompBrokerRelay(Constants.SIMPLE_BROKER)
//                .setVirtualHost(rabbitMQProperties.getVirtualHost())
//                .setRelayHost(rabbitMQProperties.getHost())
//                .setRelayPort(61613)
//                .setClientLogin(rabbitMQProperties.getUsername())
//                .setClientPasscode(rabbitMQProperties.getPassword())
//                .setSystemLogin(rabbitMQProperties.getUsername())
//                .setSystemPasscode(rabbitMQProperties.getPassword())
//                .setSystemHeartbeatSendInterval(5000)
//                .setSystemHeartbeatReceiveInterval(5000);

    }
}
