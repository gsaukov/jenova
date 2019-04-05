package com.pro.jenova.common.rest.client;

import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import static com.pro.jenova.common.util.LogCorrelation.LOG_CORRELATION_ID;
import static com.pro.jenova.common.util.LogCorrelation.useExistingOrCreateNew;
import static org.slf4j.LoggerFactory.getLogger;

public class LogCorrelationIdInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger logger = getLogger(LogCorrelationIdInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        String logCorrelationId = useExistingOrCreateNew(MDC.get(LOG_CORRELATION_ID));

        logger.debug("Adding logCorrelationId {} into headers for outgoing client request.", logCorrelationId);

        request.getHeaders().add(LOG_CORRELATION_ID, logCorrelationId);
        return execution.execute(request, body);
    }

}
