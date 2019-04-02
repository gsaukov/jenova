package com.pro.jenova.common.rest.filter;

import com.pro.jenova.common.util.audit.AuditContext;
import org.slf4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class LogAuditContextFilter extends OncePerRequestFilter {

    private static final Logger logger = getLogger(LogAuditContextFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        AuditContext context = AuditContext.getReady();

        try {
            filterChain.doFilter(request, response);
        } finally {
            logAuditContext(context);
            AuditContext.cleanup();
        }
    }

    private void logAuditContext(AuditContext context) {
        logger.debug("audit - dbQueriesCount {}, dbTimeMillis {}",
                context.getDbQueriesCount(),
                context.getDbTimeMillis());
    }

}