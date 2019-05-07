package com.pro.jenova.justitia.security;

import com.pro.jenova.justitia.data.entity.Login;
import com.pro.jenova.justitia.data.repository.LoginRepository;
import com.pro.jenova.justitia.security.sca.StrongCustomerAuthenticationToken;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidScopeException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2RequestValidator;
import org.springframework.security.oauth2.provider.TokenRequest;

import java.util.Optional;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

public class AppOAuth2RequestValidator implements OAuth2RequestValidator {

    private static final Logger logger = getLogger(AppOAuth2RequestValidator.class);

    @Autowired
    private LoginRepository loginRepository;

    public void validateScope(AuthorizationRequest authorizationRequest, ClientDetails client) throws InvalidScopeException {
        validateRequest(authorizationRequest.getScope(), client);
    }

    public void validateScope(TokenRequest tokenRequest, ClientDetails client) throws InvalidScopeException {
        // NOOP
    }

    private void validateRequest(Set<String> requestScopes, ClientDetails client) {
        Authentication authentication = getContext().getAuthentication();

        if (!(authentication instanceof StrongCustomerAuthenticationToken)) {
            onValidationFailed("Requires strong customer authentication.");
            return;
        }

        validateRequest(requestScopes, client, (StrongCustomerAuthenticationToken) authentication);
    }

    private void validateRequest(Set<String> requestScopes, ClientDetails client,
                                 StrongCustomerAuthenticationToken authentication) {
        Optional<Login> login = loginRepository.findByReference((String) authentication.getReference());

        validateRequest(requestScopes, client, login.get());
    }

    private void validateRequest(Set<String> requestScopes, ClientDetails client, Login login) {
        if (!login.getClientId().equals(client.getClientId())) {
            onValidationFailed("Client-id provided does not match the one in the authentication.");
            return;
        }

        Set<String> clientScopes = client.getScope();

        if (clientScopes != null && !clientScopes.isEmpty()) {
            for (String scope : requestScopes) {
                if (!clientScopes.contains(scope)) {
                    onValidationFailed("Scope value expected is " + login.getScopes());
                    return;
                }
            }
        }

        if (requestScopes.size() != 1 || !requestScopes.contains(login.getScopes())) {
            onValidationFailed("Scope value expected is " + login.getScopes());
        }
    }

    private void onValidationFailed(String message) {
        logger.debug(message);
        throw new OAuth2Exception(message);
    }

}
