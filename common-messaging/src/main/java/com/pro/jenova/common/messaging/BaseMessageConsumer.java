package com.pro.jenova.common.messaging;

import com.pro.jenova.common.util.audit.AuditContext;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import static com.pro.jenova.common.util.LogCorrelation.LOG_CORRELATION_ID;
import static com.pro.jenova.common.util.LogCorrelation.useExistingOrCreateNew;
import static org.slf4j.LoggerFactory.getLogger;

public abstract class BaseMessageConsumer {

    private static final Logger logger = getLogger(AuditContext.class);

    protected void consume(Message message) {
        MessageProperties properties = message.getMessageProperties();
        Object correlationId = properties.getHeaders().get(LOG_CORRELATION_ID);
        MDC.put(LOG_CORRELATION_ID, useExistingOrCreateNew(correlationId));

        AuditContext.remove();
        try {
            receive(message);
        } finally {
            logAuditContext();
            AuditContext.remove();
        }
    }

    private void logAuditContext() {
        AuditContext context = AuditContext.get();

        logger.debug("Audit (messaging) - dbQueriesCount {}, dbTimeMillis {}",
                context.getDbQueriesCount(),
                context.getDbTimeMillis());
    }

    protected abstract void receive(Message message);

}
