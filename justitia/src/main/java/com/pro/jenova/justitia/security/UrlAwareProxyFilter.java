package com.pro.jenova.justitia.security;

import org.slf4j.Logger;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UrlAwareProxyFilter extends OncePerRequestFilter {

    private static final Logger logger = getLogger(UrlAwareProxyFilter.class);

    private RequestMatcher requestMatcher;
    private Filter target;

    public UrlAwareProxyFilter(String filterProcessesUrl, Filter target) {
        this.requestMatcher = new AntPathRequestMatcher(filterProcessesUrl);
        this.target = target;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (!requestMatcher.matches(request)) {
            logger.debug("Skipping filter {}.", target.getClass());

            chain.doFilter(request, response);
            return;
        }

        target.doFilter(request, response, chain);
    }

}
