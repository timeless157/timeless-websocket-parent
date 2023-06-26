package com.timeless.constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitMQProperties {
    private String host;
    private String username;
    private String password;
    private String virtualHost;


}