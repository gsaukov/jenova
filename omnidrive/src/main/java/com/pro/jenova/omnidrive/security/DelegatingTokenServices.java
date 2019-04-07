package com.pro.jenova.omnidrive.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class DelegatingTokenServices implements ResourceServerTokenServices {

    private static final Pattern UUID = compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

    private ResourceServerTokenServices remoteTokenServices;
    private ResourceServerTokenServices defaultTokenServices;

    public void setRemoteTokenServices(ResourceServerTokenServices remoteTokenServices) {
        this.remoteTokenServices = remoteTokenServices;
    }

    public void setDefaultTokenServices(ResourceServerTokenServices defaultTokenServices) {
        this.defaultTokenServices = defaultTokenServices;
    }

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken)
            throws AuthenticationException, InvalidTokenException {
        if (isUuid(accessToken)) {
            return remoteTokenServices.loadAuthentication(accessToken);
        } else {
            return defaultTokenServices.loadAuthentication(accessToken);
        }
    }

    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        if (isUuid(accessToken)) {
            return remoteTokenServices.readAccessToken(accessToken);
        } else {
            return defaultTokenServices.readAccessToken(accessToken);
        }
    }

    private boolean isUuid(String accessToken) {
        return UUID.matcher(accessToken).matches();
    }

}
