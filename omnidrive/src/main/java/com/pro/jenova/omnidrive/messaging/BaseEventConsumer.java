package com.pro.jenova.omnidrive.msg;

import org.slf4j.MDC;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import static com.pro.jenova.omnidrive.util.IdUtils.uuid;
import static com.pro.jenova.omnidrive.util.LogCorrelation.LOG_CORRELATION_ID;
import static com.pro.jenova.omnidrive.util.LogCorrelation.MAX_ID_SIZE;

public abstract class BaseEventConsumer {

    public void prepare(Message message) {
        MessageProperties properties = message.getMessageProperties();

        Object correlationId = properties.getHeaders().get(LOG_CORRELATION_ID);

        MDC.put(LOG_CORRELATION_ID, useExistingOrCreateNew(correlationId));
    }

    public String useExistingOrCreateNew(Object correlationIdObj) {
        if (correlationIdObj == null) {
            return uuid();
        }

        String correlationId = String.class.cast(correlationIdObj);

        if (correlationId.length() > MAX_ID_SIZE) {
            return correlationId.substring(0, MAX_ID_SIZE);
        }

        return correlationId;
    }

}
