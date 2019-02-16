package com.pro.jenova.justitia.rest.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OneTimePasswordFilterFactory {

    @Bean
    public FilterRegistrationBean<OneTimePasswordFilter> oneTimePasswordFilterRegistration() {
        FilterRegistrationBean<OneTimePasswordFilter> registration = new FilterRegistrationBean<>();

        registration.setFilter(oneTimePasswordFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(2);

        return registration;
    }

    @Bean
    public OneTimePasswordFilter oneTimePasswordFilter() {
        return new OneTimePasswordFilter();
    }

}
