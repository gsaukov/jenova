package com.pro.jenova.omnidrive.messaging.user;

import com.pro.jenova.omnidrive.data.entity.User;
import com.pro.jenova.omnidrive.messaging.BaseEventProducer;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

@Component
public class UserEventProducer extends BaseEventProducer {

    @Autowired
    private Exchange exchange;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void onUserCreated(User user) {
        String message = format("Created user with identifier [{0}].", user.getId());

        send("user.created", message);
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
