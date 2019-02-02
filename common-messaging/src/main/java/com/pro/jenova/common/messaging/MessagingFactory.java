package com.pro.jenova.common.messaging;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingFactory {

    @Bean
    @ApplicationTopicExchange
    public Exchange applicationTopicExchange() {
        return new TopicExchange("application");
    }

    @Bean
    @AuditTopicExchange
    public Exchange auditTopicExchange() {
        return new TopicExchange("audit");
    }

}
