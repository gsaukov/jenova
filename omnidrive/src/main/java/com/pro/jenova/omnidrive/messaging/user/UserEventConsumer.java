package com.pro.jenova.omnidrive.messaging.user;

import com.pro.jenova.omnidrive.messaging.BaseEventConsumer;
import org.slf4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class UserEventConsumer extends BaseEventConsumer {

    private static final Logger logger = getLogger(UserEventConsumer.class);

    @RabbitListener(queues = "userEventsQueue")
    public void onMessage(Message message) {
        prepare(message);

        String content = new String(message.getBody(), UTF_8);

        logger.info("Message Content: {}", content);
    }

}
