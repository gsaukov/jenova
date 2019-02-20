package com.pro.jenova.justitia.messaging.oob;

import com.pro.jenova.common.messaging.ApplicationTopicExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.amqp.core.BindingBuilder.bind;

@Configuration
public class OutOfBandConfigurer {

    public static final String OUT_OF_BAND_ROUTE = "outOfBand";
    public static final String OUT_OF_BAND_QUEUE = "outOfBandQueue";

    @Autowired
    @ApplicationTopicExchange
    private Exchange applicationTopicExchange;

    @Bean
    public Queue outOfBandQueue() {
        return new Queue(OUT_OF_BAND_QUEUE);
    }

    @Bean
    public Binding outOfBandinding() {
        return bind(outOfBandQueue()).to(applicationTopicExchange).with(OUT_OF_BAND_ROUTE).noargs();
    }

}
