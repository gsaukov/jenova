package com.pro.jenova.omnidrive.messaging.notification;

import com.pro.jenova.common.messaging.BaseMessageConsumer;
import org.slf4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class NotificationConsumer extends BaseMessageConsumer {

    private static final Logger logger = getLogger(NotificationConsumer.class);

    @RabbitListener(queues = "notificationQueue")
    public void onMessage(Message message) {
        prepare(message);

        logger.info(message.toString());
    }

}
