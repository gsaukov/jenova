package com.pro.jenova.omnidrive.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Service
public class CustomClaimsService {

    @Autowired
    private TokenStore tokenStore;

    public Optional<Object> getClaim(String claim) {
        Authentication authentication = getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && authentication instanceof OAuth2Authentication) {
            return getClaim((OAuth2Authentication) authentication, claim);
        }

        return empty();
    }

    private Optional<Object> getClaim(OAuth2Authentication authentication, String claim) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();

        OAuth2AccessToken accessToken = tokenStore.readAccessToken(details.getTokenValue());

        Map<String, Object> additionalInfo = accessToken.getAdditionalInformation();

        if (additionalInfo != null && additionalInfo.containsKey(claim)) {
            return ofNullable(additionalInfo.get(claim));
        }

        return empty();
    }

}
