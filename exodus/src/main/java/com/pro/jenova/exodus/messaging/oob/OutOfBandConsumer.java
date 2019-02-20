package com.pro.jenova.exodus.messaging.oob;

import com.pro.jenova.common.messaging.BaseMessageConsumer;
import org.slf4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class OutOfBandConsumer extends BaseMessageConsumer {

    public static final String OUT_OF_BAND_QUEUE = "outOfBandQueue";

    private static final Logger logger = getLogger(OutOfBandConsumer.class);

    @RabbitListener(queues = OUT_OF_BAND_QUEUE)
    public void onMessage(Message message) {
        prepare(message);

        String content = new String(message.getBody(), UTF_8);

        logger.info(content);
    }

}
