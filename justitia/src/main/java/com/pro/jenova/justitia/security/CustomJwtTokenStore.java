package com.pro.jenova.justitia.security;

import com.pro.jenova.justitia.data.entity.AccessToken;
import com.pro.jenova.justitia.data.repository.AccessTokenRepository;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Map;

public class CustomJwtTokenStore extends JwtTokenStore {

    private static final int INFINITE = -1;

    private AccessTokenRepository accessTokenRepository;

    public CustomJwtTokenStore(JwtAccessTokenConverter jwtTokenEnhancer, AccessTokenRepository accessTokenRepository) {
        super(jwtTokenEnhancer);
        this.accessTokenRepository = accessTokenRepository;
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        super.storeAccessToken(token, authentication);

        Map<String, Object> info = token.getAdditionalInformation();

        accessTokenRepository.save(new AccessToken.Builder()
                .withUsageCount(0)
                .withMaxUsages(getMaxUsages(token))
                .withJti(info.get("jti").toString())
                .withEncoded(token.getValue())
                .build());
    }

    private Integer getMaxUsages(OAuth2AccessToken token) {
        Map<String, Object> additionalInformation = token.getAdditionalInformation();

        if (additionalInformation == null) {
            return INFINITE;
        }

        Object maxUsages = additionalInformation.get("maxUsages");

        if (!(maxUsages instanceof Integer)) {
            return INFINITE;
        }

        return (Integer) maxUsages;
    }

}
