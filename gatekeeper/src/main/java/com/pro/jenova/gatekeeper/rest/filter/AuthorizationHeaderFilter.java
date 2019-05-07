package com.pro.jenova.gatekeeper.rest.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

public class AuthorizationHeaderFilter extends ZuulFilter {

    private static final Logger logger = getLogger(AuthorizationHeaderFilter.class);

    private static final String AUTHORIZATION_HEADER = "authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String BASIC_PREFIX = "Basic ";

    @Autowired
    private AccessTokenSupport accessTokenSupport;

    @Autowired
    private BasicAuthSupport basicAuthSupport;

    @Value("#{'${feign.oauth2.supported-apis}'.split(',')}")
    private List<String> supportedApis;

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

        return null; // Not used by the framework.
    }

    private void process(RequestContext currentContext, HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (authorizationHeader != null) {
            process(currentContext, authorizationHeader);
        }
    }

    private void process(RequestContext currentContext, String authorizationHeader) {
        String uri = currentContext.getRequest().getRequestURI();
        if (supportedApis.stream().noneMatch(uri::contains)) {
            return;
        }

        String lowerAuthorizationHeader = authorizationHeader.toLowerCase();
        if (lowerAuthorizationHeader.startsWith(BEARER_PREFIX.toLowerCase())) {
            processBearer(currentContext, authorizationHeader);
        } else if (lowerAuthorizationHeader.startsWith(BASIC_PREFIX.toLowerCase())) {
            processBasic(currentContext, authorizationHeader);
        }
    }

    private void processBasic(RequestContext currentContext, String authorizationHeader) {
        String basic = authorizationHeader.substring(BASIC_PREFIX.length()).trim();

        basicAuthSupport.getToken(basic).ifPresent(accessToken -> {
            logger.debug("Replacing basic auth {} with encoded jwt value.", basic);
            currentContext.addZuulRequestHeader(AUTHORIZATION_HEADER, BEARER_PREFIX.concat(accessToken));
        });
    }

    private void processBearer(RequestContext currentContext, String authorizationHeader) {
        String providedToken = authorizationHeader.substring(BEARER_PREFIX.length()).trim();

        try {
            doProcessBearer(currentContext, providedToken);
        } catch (IllegalArgumentException exc) {
            currentContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            currentContext.setResponseBody("Access Token - Max Usages Reached");
            currentContext.setSendZuulResponse(false);
        }
    }

    private void doProcessBearer(RequestContext currentContext, String providedToken) {
        Optional<String> replacementToken = accessTokenSupport.processBearer(providedToken);

        replacementToken.ifPresent(encodedToken -> {
            logger.debug("Replacing bearer token jti {} with encoded jwt value {}.", providedToken, encodedToken);
            currentContext.addZuulRequestHeader(AUTHORIZATION_HEADER, BEARER_PREFIX.concat(encodedToken));
        });
    }

}
