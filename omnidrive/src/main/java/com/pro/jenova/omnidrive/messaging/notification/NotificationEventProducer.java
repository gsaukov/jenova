package com.pro.jenova.omnidrive.messaging.notification;

import com.pro.jenova.omnidrive.messaging.BaseEventProducer;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventProducer extends BaseEventProducer {

    @Autowired
    private Exchange exchange;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void notify(String subject, String message) {
        send("notification.".concat(subject), message);
    }

    @Override
    protected RabbitTemplate getTemplate() {
        return rabbitTemplate;
    }

    @Override
    protected Exchange getExchange() {
        return exchange;
    }

}
