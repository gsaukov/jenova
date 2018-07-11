package com.pro.jenova.omnidrive.msg.user;

import com.pro.jenova.omnidrive.data.entity.User;
import com.pro.jenova.omnidrive.msg.JenovaTopicExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

@Component
public class UserEventProducer {

    @Autowired
    @JenovaTopicExchange
    private Exchange exchange;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void onUserCreated(User user) {
        String routingKey = "user.created";
        String message = format("Created user with identifier [{0}].", user.getId());

        rabbitTemplate.convertAndSend(exchange.getName(), routingKey, message);
    }

}
