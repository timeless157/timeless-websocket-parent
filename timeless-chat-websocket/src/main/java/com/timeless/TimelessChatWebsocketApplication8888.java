package com.timeless;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * @author timeless
 * @create 2023-06-22 23:43
 */
@SpringBootApplication
@MapperScan("com.timeless.mapper")
@PropertySource("classpath:secrets.txt")
public class TimelessChatWebsocketApplication8888 {
    public static void main(String[] args) {
        SpringApplication.run(TimelessChatWebsocketApplication8888.class, args);
    }
}
