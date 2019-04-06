package com.pro.jenova.common.messaging;

import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static com.pro.jenova.common.util.LogCorrelation.LOG_CORRELATION_ID;
import static com.pro.jenova.common.util.LogCorrelation.useExistingOrCreateNew;
import static org.slf4j.LoggerFactory.getLogger;

public abstract class BaseMessageProducer {

    private static final Logger logger = getLogger(BaseMessageProducer.class);

    protected void send(String routingKey, Object message) {
        getTemplate().convertAndSend(getExchange().getName(), routingKey, message, msg -> {
            MessageProperties properties = msg.getMessageProperties();

            String logCorrelationId = useExistingOrCreateNew(MDC.get(LOG_CORRELATION_ID));
            logger.debug("Adding logCorrelationId {} into headers for outgoing message.", logCorrelationId);

            properties.getHeaders().put(LOG_CORRELATION_ID, logCorrelationId);
            return msg;
        });
    }

    protected abstract RabbitTemplate getTemplate();

    protected abstract Exchange getExchange();

}
