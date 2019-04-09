package com.pro.jenova.gatekeeper.rest.client;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfigurer {

    @Value("${feign.oauth2.client.username}")
    private String username;

    @Value("${feign.oauth2.client.password}")
    private String password;

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(username, password);
    }

    @Bean
    public LogCorrelationIdInterceptor logCorrelationIdInterceptor() {
        return new LogCorrelationIdInterceptor();
    }

}
