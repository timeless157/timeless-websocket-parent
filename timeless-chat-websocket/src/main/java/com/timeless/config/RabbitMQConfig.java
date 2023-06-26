package com.timeless.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author timeless
 * @create 2023-06-24 22:42
 */
@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue queue() {
        System.out.println("----");
        return QueueBuilder.durable("testQueue").build();
    }

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.topicExchange("testExchange").build();
    }

    @Bean
    public Binding binding(@Qualifier("queue") Queue queue, @Qualifier("exchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("test.#").noargs();
    }

}
