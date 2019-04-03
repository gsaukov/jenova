package com.pro.jenova.omnidrive.messaging.notification;

import com.pro.jenova.common.messaging.BaseMessageConsumer;
import com.pro.jenova.omnidrive.data.entity.Notification;
import com.pro.jenova.omnidrive.data.repository.NotificationRepository;
import org.slf4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.pro.jenova.omnidrive.messaging.notification.NotificationConfigurer.NOTIFICATION_QUEUE;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class NotificationConsumer extends BaseMessageConsumer {

    private static final Logger logger = getLogger(NotificationConsumer.class);

    @Autowired
    private NotificationRepository notificationRepository;

    @RabbitListener(queues = NOTIFICATION_QUEUE)
    public void onMessage(Message message) {
        consume(message);
    }

    @Override
    protected void receive(Message message) {
        String content = new String(message.getBody(), UTF_8);

        notificationRepository.save(new Notification.Builder()
                .withContent(content)
                .build());

        logger.info(content);
    }

}
