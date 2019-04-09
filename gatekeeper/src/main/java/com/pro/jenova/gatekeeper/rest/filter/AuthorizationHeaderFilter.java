package com.pro.jenova.gatekeeper.rest.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.pro.jenova.gatekeeper.data.entity.AccessToken;
import com.pro.jenova.gatekeeper.data.repository.AccessTokenRepository;
import com.pro.jenova.gatekeeper.rest.client.OAuth2Client;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;
import static org.slf4j.LoggerFactory.getLogger;

public class AuthorizationHeaderFilter extends ZuulFilter {

    private static final Logger logger = getLogger(AuthorizationHeaderFilter.class);

    private static final Pattern UUID_PATTERN = compile("^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$");

    private static final String AUTHORIZATION_HEADER = "authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    @Autowired
    private OAuth2Client oAuth2Client;

    @Value("${feign.oauth2.client-id}")
    private String clientId;

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
        if (authorizationHeader.toLowerCase().startsWith(BEARER_PREFIX.toLowerCase())) {
            doProcess(currentContext, authorizationHeader);
        }
    }

    private void doProcess(RequestContext currentContext, String authorizationHeader) {
        String token = authorizationHeader.substring(BEARER_PREFIX.length()).trim();

        if (isUuid(token)) {
            replaceHeader(currentContext, token);
        }
    }

    private void replaceHeader(RequestContext currentContext, String jti) {
        accessTokenRepository.findByJti(jti)
                .ifPresent(accessToken -> process(currentContext, accessToken));
    }

    private void process(RequestContext currentContext, AccessToken accessToken) {
        logger.debug("Replacing bearer token having jti {} with encoded jwt value.", accessToken.getJti());
        currentContext.addZuulRequestHeader(AUTHORIZATION_HEADER, BEARER_PREFIX.concat(accessToken.getEncoded()));

        try {
            Map<String, String> formParams = new HashMap<>();
            formParams.put("username", "dimitrios");
            formParams.put("password", "dimitrios");
            JsonNode result = oAuth2Client.getToken("password", clientId, formParams);
            logger.warn(result.textValue());
        } catch (Exception exc) {
            logger.error(exc.getMessage());
        }
    }

    private boolean isUuid(String value) {
        return UUID_PATTERN.matcher(value).matches();
    }

}
