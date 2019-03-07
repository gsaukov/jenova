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
        try {
            filterChain.doFilter(request, response);
        } finally {
            AuditContext auditContext = AuditContext.DIAGNOSTICS.get();
            logger.debug("dbQueriesCount {}, dbTimeMillis {}",
                    auditContext.getDbQueriesCount(),
                    auditContext.getDbTimeMillis());
            AuditContext.DIAGNOSTICS.remove();
        }
    }

}