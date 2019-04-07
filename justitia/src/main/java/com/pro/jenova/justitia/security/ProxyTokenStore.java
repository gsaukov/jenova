package com.pro.jenova.justitia.security;

import org.slf4j.Logger;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.Collection;

import static org.slf4j.LoggerFactory.getLogger;

public class ProxyTokenStore implements TokenStore {

    private static final Logger logger = getLogger(ProxyTokenStore.class);

    private TokenStore target;

    public void setTarget(TokenStore target) {
        this.target = target;
    }

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        logger.debug("Method 'OAuth2Authentication readAuthentication(OAuth2AccessToken token)' invoked.");

        return target.readAuthentication(token);
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        logger.debug("Method 'OAuth2Authentication readAuthentication(String token)' invoked.");

        return target.readAuthentication(token);
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        logger.debug("Method 'void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication)' invoked.");

        target.storeAccessToken(token, authentication);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        logger.debug("Method 'OAuth2AccessToken readAccessToken(String tokenValue)' invoked.");

        return target.readAccessToken(tokenValue);
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        logger.debug("Method 'void removeAccessToken(OAuth2AccessToken token)' invoked.");

        target.removeAccessToken(token);
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        logger.debug("Method 'void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication)' invoked.");

        target.storeRefreshToken(refreshToken, authentication);
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        logger.debug("Method 'OAuth2RefreshToken readRefreshToken(String tokenValue)' invoked.");

        return target.readRefreshToken(tokenValue);
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        logger.debug("Method 'OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token)' invoked.");

        return target.readAuthenticationForRefreshToken(token);
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        logger.debug("Method 'void removeRefreshToken(OAuth2RefreshToken token)' invoked.");

        target.removeRefreshToken(token);
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        logger.debug("Method 'void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken)' invoked.");

        target.removeAccessTokenUsingRefreshToken(refreshToken);
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        logger.debug("Method 'OAuth2AccessToken getAccessToken(OAuth2Authentication authentication)' invoked.");

        return target.getAccessToken(authentication);
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        logger.debug("Method 'Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName)' invoked.");

        return target.findTokensByClientIdAndUserName(clientId, userName);
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        logger.debug("Method 'Collection<OAuth2AccessToken> findTokensByClientId(String clientId)' invoked.");

        return target.findTokensByClientId(clientId);
    }

}
