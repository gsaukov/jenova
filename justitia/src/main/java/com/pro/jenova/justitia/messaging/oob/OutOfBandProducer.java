package com.pro.jenova.justitia.messaging.oob;

import com.pro.jenova.common.messaging.ApplicationTopicExchange;
import com.pro.jenova.common.messaging.BaseMessageProducer;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.pro.jenova.justitia.messaging.oob.OutOfBandConfigurer.OUT_OF_BAND_ROUTE;


@Component
public class OutOfBandProducer extends BaseMessageProducer {

    @Autowired
    @ApplicationTopicExchange
    private Exchange applicationTopicExchange;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void initOutOfBand(String username) {
        StringBuilder builder = new StringBuilder();

        builder.append("Dear ").append(username)
                .append(", please authenticate yourself on your smart-phone.");

        send(OUT_OF_BAND_ROUTE, builder.toString());
    }

    @Override
    protected RabbitTemplate getTemplate() {
        return rabbitTemplate;
    }

    @Override
    protected Exchange getExchange() {
        return applicationTopicExchange;
    }

}
