package com.pro.jenova.omnidrive.msg.user;

import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class UserEventConsumer {

    private static final Logger logger = getLogger(UserEventConsumer.class);

    @RabbitListener(queues = "userEventsQueue")
    public void receive(String message) {
        logger.info("Message Received: {}", message);
    }

}
