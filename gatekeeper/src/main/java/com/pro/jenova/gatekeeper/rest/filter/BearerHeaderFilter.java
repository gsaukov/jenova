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
    private static final String BEARER_PREFIX = "bearer ";
    private static final int JTI_LENGTH = 36;

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

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();

            if (AUTHORIZATION_HEADER.equalsIgnoreCase(request.getHeader(headerName))) {
                process(currentContext, headerName);
            }
        }

        return null;
    }

    private void process(RequestContext currentContext, String authorizationHeader) {
        if (!isBearer(authorizationHeader)) {
            return;
        }

        String token = authorizationHeader.substring(BEARER_PREFIX.length()).trim();

        if (isJti(token)) {
            accessTokenRepository.findByJti(token).ifPresent(accessToken
                    -> process(currentContext, authorizationHeader, accessToken));
        }
    }

    private void process(RequestContext currentContext, String authorizationHeader, AccessToken accessToken) {
        logger.debug("Replacing bearer token having jti {} with encoded jwt value.", accessToken.getJti());

        currentContext.addZuulRequestHeader(authorizationHeader, BEARER_PREFIX.concat(accessToken.getEncoded()));
    }

    private boolean isBearer(String header) {
        return header.toLowerCase().startsWith(BEARER_PREFIX);
    }

    private boolean isJti(String token) {
        return JTI_LENGTH == token.length();
    }

}
