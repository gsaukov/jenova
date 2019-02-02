package com.pro.jenova.omnidrive.messaging.notification;

import com.pro.jenova.common.messaging.ApplicationTopicExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.amqp.core.BindingBuilder.bind;

@Configuration
public class NotificationConfigurer {

    @Autowired
    @ApplicationTopicExchange
    private Exchange applicationTopicExchange;

    @Bean
    public Queue notificationQueue() {
        return new Queue("notificationQueue");
    }

    @Bean
    public Binding notificationQueueBinding() {
        return bind(notificationQueue()).to(applicationTopicExchange).with("notification").noargs();
    }

}
