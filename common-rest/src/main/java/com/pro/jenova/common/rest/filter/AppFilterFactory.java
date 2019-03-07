package com.pro.jenova.common.rest.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppFilterFactory {

    @Bean
    public FilterRegistrationBean<LogCorrelationIdFilter> logCorrelationIdFilterRegistration() {
        FilterRegistrationBean<LogCorrelationIdFilter> registration = new FilterRegistrationBean<>();

        registration.setFilter(logCorrelationIdFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(0);

        return registration;
    }

    @Bean
    public LogCorrelationIdFilter logCorrelationIdFilter() {
        return new LogCorrelationIdFilter();
    }

    @Bean
    public FilterRegistrationBean<LogAuditContextFilter> logAuditContextFilterRegistration() {
        FilterRegistrationBean<LogAuditContextFilter> registration = new FilterRegistrationBean<>();

        registration.setFilter(logAuditContextFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(1);

        return registration;
    }

    @Bean
    public LogAuditContextFilter logAuditContextFilter() {
        return new LogAuditContextFilter();
    }

    @Bean
    public FilterRegistrationBean<RequestAndResponseFilter> requestAndResponseLoggerFilterRegistration() {
        FilterRegistrationBean<RequestAndResponseFilter> registration = new FilterRegistrationBean<>();

        registration.setFilter(requestAndResponseLoggerFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(2);

        return registration;
    }

    @Bean
    public RequestAndResponseFilter requestAndResponseLoggerFilter() {
        return new RequestAndResponseFilter();
    }

}
