package com.pro.jenova.gatekeeper.rest.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;

import static com.pro.jenova.common.util.LogCorrelation.LOG_CORRELATION_ID;
import static com.pro.jenova.common.util.LogCorrelation.useExistingOrCreateNew;

public class LogCorrelationIdInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String logCorrelationId = useExistingOrCreateNew(MDC.get(LOG_CORRELATION_ID));
        requestTemplate.header(LOG_CORRELATION_ID, logCorrelationId);
    }

}

