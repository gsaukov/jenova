package com.pro.jenova.blackstone.rest.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;


@Configuration
public class FilterFactory {

    @Bean
    public FilterRegistrationBean requestAndResponseLoggerRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();

        registration.setFilter(requestAndResponseLoggerFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(2);

        return registration;
    }

    @Bean
    public Filter requestAndResponseLoggerFilter() {
        return new RequestAndResponseLogger();
    }

    @Bean
    public FilterRegistrationBean correlationLoggerRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();

        registration.setFilter(correlationLoggerFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(1);

        return registration;
    }

    @Bean
    public Filter correlationLoggerFilter() {
        return new CorrelationLogger();
    }

}
