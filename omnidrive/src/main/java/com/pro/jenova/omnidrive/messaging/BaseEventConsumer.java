package com.pro.jenova.omnidrive.messaging;

import org.slf4j.MDC;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import static com.pro.jenova.common.util.LogCorrelation.LOG_CORRELATION_ID;
import static com.pro.jenova.common.util.LogCorrelation.useExistingOrCreateNew;

public abstract class BaseEventConsumer {

    public void prepare(Message message) {
        MessageProperties properties = message.getMessageProperties();

        Object correlationId = properties.getHeaders().get(LOG_CORRELATION_ID);

        MDC.put(LOG_CORRELATION_ID, useExistingOrCreateNew(correlationId));
    }

}
