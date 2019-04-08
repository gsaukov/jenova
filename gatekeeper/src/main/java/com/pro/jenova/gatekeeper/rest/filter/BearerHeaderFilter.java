package com.pro.jenova.gatekeeper.rest.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.pro.jenova.gatekeeper.data.entity.AccessToken;
import com.pro.jenova.gatekeeper.data.repository.AccessTokenRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

import static org.slf4j.LoggerFactory.getLogger;

public class BearerHeaderFilter extends ZuulFilter {

    private static final Logger logger = getLogger(BearerHeaderFilter.class);

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final Integer JTI_LENGTH = 36;
    private static final Object NOT_USED = null;

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        process(currentContext, request);

        return NOT_USED;
    }

    private void process(RequestContext currentContext, HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();

            if (AUTHORIZATION_HEADER.equalsIgnoreCase(headerName)) {
                process(currentContext, request.getHeader(headerName));
                break;
            }
        }
    }

    private void process(RequestContext currentContext, String authorizationHeader) {
        if (authorizationHeader.toLowerCase().startsWith(BEARER_PREFIX.toLowerCase())) {
            doProcess(currentContext, authorizationHeader);
        }
    }

    private void doProcess(RequestContext currentContext, String authorizationHeader) {
        String token = authorizationHeader.substring(BEARER_PREFIX.length()).trim();

        if (JTI_LENGTH.equals(token.length())) {
            replaceHeader(currentContext, authorizationHeader, token);
        }
    }

    private void replaceHeader(RequestContext currentContext, String authorizationHeader, String jti) {
        accessTokenRepository.findByJti(jti)
                .ifPresent(accessToken -> process(currentContext, authorizationHeader, accessToken));
    }

    private void process(RequestContext currentContext, String authorizationHeader, AccessToken accessToken) {
        logger.debug("Replacing bearer token having jti {} with encoded jwt value.", accessToken.getJti());
        currentContext.addZuulRequestHeader(authorizationHeader, BEARER_PREFIX.concat(accessToken.getEncoded()));
    }

}
