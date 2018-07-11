package com.pro.jenova.omnidrive.msg;

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
    @JenovaTopicExchange
    public Exchange jenovaTopicExchange() {
        return new TopicExchange("jenova");
    }

    @Bean
    @UserEventsQueue
    public Queue userEventsQueue() {
        return new Queue("userEventsQueue");
    }

    @Bean
    public Binding userEventsBinding(@UserEventsQueue Queue queue, @JenovaTopicExchange Exchange exchange) {
        return bind(queue).to(exchange).with("user.*").noargs();
    }

}
