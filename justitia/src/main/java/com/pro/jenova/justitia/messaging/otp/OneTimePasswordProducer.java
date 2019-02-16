package com.pro.jenova.justitia.messaging.otp;

import com.pro.jenova.common.messaging.ApplicationTopicExchange;
import com.pro.jenova.common.messaging.BaseMessageProducer;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.pro.jenova.justitia.messaging.otp.OneTimePasswordConfigurer.ONE_TIME_PASSWORD_ROUTE;


@Component
public class OneTimePasswordProducer extends BaseMessageProducer {

    @Autowired
    @ApplicationTopicExchange
    private Exchange applicationTopicExchange;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String username, String oneTimePassword) {
        StringBuilder builder = new StringBuilder();

        builder.append("Dear ").append(username)
                .append(", your one time password is ")
                .append(oneTimePassword).append(".");

        send(ONE_TIME_PASSWORD_ROUTE, builder.toString());
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
