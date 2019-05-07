/*
 * Cloud Foundry 2012.02.03 Beta
 * Copyright (c) [2009-2012] VMware, Inc. All Rights Reserved.
 *
 * This product is licensed to you under the Apache License, Version 2.0 (the "License").
 * You may not use this product except in compliance with the License.
 *
 * This product includes a number of subcomponents with
 * separate copyright notices and license terms. Your use of these
 * subcomponents is subject to the terms and conditions of the
 * subcomponent's license, as noted in the LICENSE file.
 */
package com.pro.jenova.justitia.security;

import com.pro.jenova.justitia.data.entity.Scope;
import com.pro.jenova.justitia.data.repository.LoginRepository;
import com.pro.jenova.justitia.data.repository.ScopeRepository;
import com.pro.jenova.justitia.security.sca.StrongCustomerAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.*;

import static java.util.Comparator.comparing;
import static org.apache.commons.lang.StringUtils.splitPreserveAllTokens;

public class AppJwtAccessTokenConverter extends JwtAccessTokenConverter {

    @Autowired
    private ScopeRepository scopeRepository;

    @Autowired
    private LoginRepository loginRepository;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
        customAccessToken.setAdditionalInformation(getAdditionalInfo(authentication));

        return super.enhance(customAccessToken, authentication);
    }

    private Map<String, Object> getAdditionalInfo(OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();

        enhanceWithScopeInfo(authentication, additionalInfo);
        enhanceWithCustomParams(authentication.getUserAuthentication(), additionalInfo);

        return additionalInfo;
    }

    private void enhanceWithCustomParams(Authentication authentication, Map<String, Object> additionalInfo) {
        if (authentication instanceof StrongCustomerAuthenticationToken) {
            enhanceWithCustomParams((StrongCustomerAuthenticationToken) authentication, additionalInfo);
        }
    }

    private void enhanceWithCustomParams(StrongCustomerAuthenticationToken authentication,
                                         Map<String, Object> additionalInfo) {

        String reference = (String) authentication.getReference();
        loginRepository.findWithParamsByReference(reference).ifPresent(login -> additionalInfo.putAll(login.getParams()));
    }

    private void enhanceWithScopeInfo(OAuth2Authentication authentication, Map<String, Object> additionalInfo) {
        additionalInfo.put("permissions", getPermissions(authentication));
        additionalInfo.put("maxUsages", getMaxUsages(authentication));
    }

    private Integer getMaxUsages(OAuth2Authentication authentication) {
        OAuth2Request request = authentication.getOAuth2Request();
        String clientId = request.getClientId();

        return request.getScope().stream()
                .map(scope -> getMaxUsages(clientId, scope))
                .filter(Objects::nonNull)
                .filter(maxUsages -> maxUsages > 0)
                .min(comparing(Integer::valueOf))
                .orElse(null);
    }

    private Integer getMaxUsages(String clientId, String scopeName) {
        return scopeRepository.findByClientIdAndName(clientId, scopeName).map(Scope::getMaxUsages).orElse(null);
    }

    private Set<String> getPermissions(OAuth2Authentication authentication) {
        Set<String> permissions = new HashSet<>();

        OAuth2Request request = authentication.getOAuth2Request();
        String clientId = request.getClientId();

        request.getScope().forEach(scope -> enhanceWithScopePermissions(clientId, scope, permissions));

        return permissions;
    }

    private void enhanceWithScopePermissions(String clientId, String scopeName, Set<String> permissions) {
        scopeRepository.findByClientIdAndName(clientId, scopeName).ifPresent(scope -> {
            appendPermissions(permissions, splitPreserveAllTokens(scope.getPermissions(), ","));
        });
    }

    private void appendPermissions(Set<String> permissions, String[] items) {
        if (items == null) {
            return;
        }

        for (String item : items) {
            permissions.add(item.trim());
        }
    }

}