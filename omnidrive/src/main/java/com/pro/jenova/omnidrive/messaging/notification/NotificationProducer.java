package com.pro.jenova.omnidrive.messaging.notification;

import com.pro.jenova.common.messaging.ApplicationTopicExchange;
import com.pro.jenova.common.messaging.BaseMessageProducer;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.pro.jenova.omnidrive.messaging.notification.NotificationConfigurer.NOTIFICATION_ROUTE;

@Component
public class NotificationProducer extends BaseMessageProducer {

    @Autowired
    @ApplicationTopicExchange
    private Exchange applicationTopicExchange;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendNotification(String message) {
        send(NOTIFICATION_ROUTE, message);
    }

    @Override
    protected RabbitTemplate getTemplate() {
        return rabbitTemplate;
    }

    @Override
    protected Exchange getExchange() {
        return applicationTopicExchange;
    }

}
