package com.pro.jenova.justitia.security;

import com.pro.jenova.common.rest.filter.RequestAndResponseFilter;
import com.pro.jenova.justitia.data.entity.AccessToken;
import com.pro.jenova.justitia.data.repository.AccessTokenRepository;
import com.pro.jenova.justitia.data.repository.RefreshTokenRepository;
import org.slf4j.Logger;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

public class CustomJwtTokenStore extends JwtTokenStore {

    private static final Logger logger = getLogger(CustomJwtTokenStore.class);

    private AccessTokenRepository accessTokenRepository;
    private RefreshTokenRepository refreshTokenRepository;

    public CustomJwtTokenStore(JwtAccessTokenConverter jwtTokenEnhancer) {
        super(jwtTokenEnhancer);
    }

    public void setAccessTokenRepository(AccessTokenRepository accessTokenRepository) {
        this.accessTokenRepository = accessTokenRepository;
    }

    public void setRefreshTokenRepository(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        super.storeAccessToken(token, authentication);

        Map<String, Object> additionalInformation = token.getAdditionalInformation();
        logger.info(additionalInformation.toString());
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        super.storeRefreshToken(refreshToken, authentication);

        logger.info(refreshToken.toString());
    }

}
