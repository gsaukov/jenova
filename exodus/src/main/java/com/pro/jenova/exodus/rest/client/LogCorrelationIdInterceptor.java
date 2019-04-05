/*
package com.pro.jenova.exodus.rest.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;

import static com.pro.jenova.common.util.LogCorrelation.LOG_CORRELATION_ID;
import static com.pro.jenova.common.util.LogCorrelation.useExistingOrCreateNew;

// FIXME
@Configuration
public class LogCorrelationIdInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(LOG_CORRELATION_ID, useExistingOrCreateNew(MDC.get(LOG_CORRELATION_ID)));
    }

}
*/
