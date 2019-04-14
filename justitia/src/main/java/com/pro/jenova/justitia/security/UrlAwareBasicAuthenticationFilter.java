package com.pro.jenova.justitia.security;

import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UrlAwareBasicAuthenticationFilter extends BasicAuthenticationFilter {

    private static final Logger logger = getLogger(UrlAwareBasicAuthenticationFilter.class);

    private RequestMatcher requestMatcher;

    public UrlAwareBasicAuthenticationFilter(AuthenticationManager authenticationManager, String filterProcessesUrl) {
        super(authenticationManager);
        this.requestMatcher = new AntPathRequestMatcher(filterProcessesUrl);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (!requestMatcher.matches(request)) {
            logger.debug("Skipping filter UrlAwareBasicAuthenticationFilter.");
            chain.doFilter(request, response);
            return;
        }

        logger.debug("Delegating to filter BasicAuthenticationFilter.");
        super.doFilterInternal(request, response, chain);
    }

}
