package com.pro.jenova.common.messaging;

import org.slf4j.MDC;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static com.pro.jenova.common.util.LogCorrelation.LOG_CORRELATION_ID;
import static com.pro.jenova.common.util.LogCorrelation.useExistingOrCreateNew;

public abstract class BaseMessageProducer {

    protected void send(String routingKey, Object message) {
        getTemplate().convertAndSend(getExchange().getName(), routingKey, message, msg -> {
            MessageProperties properties = msg.getMessageProperties();
            properties.getHeaders().put(LOG_CORRELATION_ID, useExistingOrCreateNew(MDC.get(LOG_CORRELATION_ID)));
            return msg;
        });
    }

    protected abstract RabbitTemplate getTemplate();

    protected abstract Exchange getExchange();

}
