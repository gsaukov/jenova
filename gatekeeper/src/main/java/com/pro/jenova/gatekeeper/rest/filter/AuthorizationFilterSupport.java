package com.pro.jenova.gatekeeper.rest.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.pro.jenova.gatekeeper.rest.client.OAuth2Client;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class AuthorizationFilterSupport {

    private static final Logger logger = getLogger(AuthorizationFilterSupport.class);

    private static final String PASSWORD_GRANT_TYPE = "password";

    @Autowired
    private OAuth2Client oAuth2Client;

    @Value("${feign.oauth2.client-id}")
    private String clientId;

    public Optional<String> getToken(String username, String password) {
        try {
            JsonNode result = oAuth2Client.getToken(PASSWORD_GRANT_TYPE, clientId, toFormParams(username, password));
            logger.warn(result.textValue());
        } catch (Exception exc) {
            logger.error(exc.getMessage());
        }
        return empty();
    }

    private Map<String, String> toFormParams(String username, String password) {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("username", username);
        formParams.put("password", password);
        return formParams;
    }

}
