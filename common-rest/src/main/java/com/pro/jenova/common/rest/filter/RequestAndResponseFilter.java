package com.pro.jenova.common.rest.filter;

import org.slf4j.Logger;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

import static org.slf4j.LoggerFactory.getLogger;

public class RequestAndResponseFilter extends AbstractRequestLoggingFilter {

    private static final Logger logger = getLogger(RequestAndResponseFilter.class);

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return logger.isDebugEnabled();
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        logger.debug(message);
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        logger.debug(message);
    }

}
