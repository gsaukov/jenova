package com.pro.jenova.omnidrive.security;

import org.slf4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;
import static org.slf4j.LoggerFactory.getLogger;

public class DelegatingTokenServices implements ResourceServerTokenServices {

    private static final Logger logger = getLogger(DelegatingTokenServices.class);

    private static final Pattern UUID = compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

    private RemoteTokenServices remoteTokenServices;
    private DefaultTokenServices defaultTokenServices;

    public void setRemoteTokenServices(RemoteTokenServices remoteTokenServices) {
        this.remoteTokenServices = remoteTokenServices;
    }

    public void setDefaultTokenServices(DefaultTokenServices defaultTokenServices) {
        this.defaultTokenServices = defaultTokenServices;
    }

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken)
            throws AuthenticationException, InvalidTokenException {
        if (isUuid(accessToken)) {
            logger.debug("UUID {} found, so using remoteTokenServices.", accessToken);
            return remoteTokenServices.loadAuthentication(accessToken);
        } else {
            logger.debug("JWT {} found, so using defaultTokenServices.", accessToken);
            return defaultTokenServices.loadAuthentication(accessToken);
        }
    }

    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        if (isUuid(accessToken)) {
            logger.debug("UUID {} found, so using remoteTokenServices.", accessToken);
            return remoteTokenServices.readAccessToken(accessToken);
        } else {
            logger.debug("JWT {} found, so using defaultTokenServices.", accessToken);
            return defaultTokenServices.readAccessToken(accessToken);
        }
    }

    private boolean isUuid(String accessToken) {
        return UUID.matcher(accessToken).matches();
    }

}
