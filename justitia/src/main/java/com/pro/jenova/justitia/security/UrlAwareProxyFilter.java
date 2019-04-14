package com.pro.jenova.justitia.security;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UrlAwareProxyFilter extends OncePerRequestFilter {

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
            chain.doFilter(request, response);
            return;
        }

        target.doFilter(request, response, chain);
    }

}
