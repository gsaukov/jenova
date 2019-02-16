package com.pro.jenova.justitia.messaging.otp;

import com.pro.jenova.common.messaging.ApplicationTopicExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.amqp.core.BindingBuilder.bind;

@Configuration
public class OneTimePasswordConfigurer {

    public static final String ONE_TIME_PASSWORD_ROUTE = "oneTimePassword";
    public static final String ONE_TIME_PASSWORD_QUEUE = "oneTimePasswordQueue";

    @Autowired
    @ApplicationTopicExchange
    private Exchange applicationTopicExchange;

    @Bean
    public Queue oneTimePasswordQueue() {
        return new Queue(ONE_TIME_PASSWORD_QUEUE);
    }

    @Bean
    public Binding notificationQueueBinding() {
        return bind(oneTimePasswordQueue()).to(applicationTopicExchange).with(ONE_TIME_PASSWORD_ROUTE).noargs();
    }

}
