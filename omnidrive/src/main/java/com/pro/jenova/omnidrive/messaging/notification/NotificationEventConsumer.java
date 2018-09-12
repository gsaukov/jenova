package com.pro.jenova.omnidrive.messaging.notification;

import com.pro.jenova.omnidrive.messaging.BaseEventConsumer;
import com.pro.jenova.omnidrive.rest.controller.client.EchoClient;
import org.slf4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class NotificationEventConsumer extends BaseEventConsumer {

    private static final Logger logger = getLogger(NotificationEventConsumer.class);

    @Autowired
    private EchoClient echoClient;

    @RabbitListener(queues = "notificationEventsQueue")
    public void onMessage(Message message) {
        prepare(message);

        String content = new String(message.getBody(), UTF_8);

        logger.info("JMS Notification: {}", content);

        echoClient.echo(Integer.parseInt(content));
    }

}
