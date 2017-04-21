package com.pro.jenova.omnidrive.rest.filter;


import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.pro.jenova.omnidrive.util.IdUtils.uuid;

public class CorrelationLogger extends OncePerRequestFilter {

    private static final String LOG_CORRELATION_ID = "logCorrelationId";

    private static final int MAX_ID_SIZE = 50;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String correlationId = request.getHeader(LOG_CORRELATION_ID);

        MDC.put(LOG_CORRELATION_ID, verifyOrCreate(correlationId));

        filterChain.doFilter(request, response);
    }

    public String verifyOrCreate(String correlationId) {
        if (correlationId == null) {
            return uuid();
        }

        if (correlationId.length() > MAX_ID_SIZE) {
            return correlationId.substring(0, MAX_ID_SIZE);
        }

        return correlationId;
    }

}