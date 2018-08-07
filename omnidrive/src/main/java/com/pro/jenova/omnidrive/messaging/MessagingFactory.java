package com.pro.jenova.omnidrive.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.amqp.core.BindingBuilder.bind;

@Configuration
public class MessagingFactory {

    @Bean
    public Exchange jenovaTopicExchange() {
        return new TopicExchange("jenova");
    }

    @Bean
    public Queue userEventsQueue() {
        return new Queue("userEventsQueue");
    }

    @Bean
    public Binding userEventsBinding() {
        return bind(userEventsQueue()).to(jenovaTopicExchange()).with("user.*").noargs();
    }

}
