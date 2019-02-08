package com.pro.jenova.justitia.rest.controller.client.request;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;


public class RestCreateClientRequest implements ClientDetails {

    private static final Set<String> GRANT_TYPES = new HashSet<>(asList("authorization_code", "refresh_token"));

    private String clientId;
    private String clientSecret;
    private Set<String> scopes;
    private String redirectUri;
    private Integer accessTokenDurationSecs;
    private Integer refreshTokenDurationSecs;

    private RestCreateClientRequest() {
        // REST
    }

    private RestCreateClientRequest(Builder builder) {
        clientId = builder.clientId;
        clientSecret = builder.clientSecret;
        scopes = builder.scopes;
        redirectUri = builder.redirectUri;
        accessTokenDurationSecs = builder.accessTokenDurationSecs;
        refreshTokenDurationSecs = builder.refreshTokenDurationSecs;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        return emptySet();
    }

    @Override
    public boolean isSecretRequired() {
        return clientSecret != null;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Override
    public boolean isScoped() {
        return scopes != null && !scopes.isEmpty();
    }

    @Override
    public Set<String> getScope() {
        return scopes;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return GRANT_TYPES;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return new HashSet<>(asList(redirectUri));
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return emptySet();
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenDurationSecs;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenDurationSecs;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }

    public Set<String> getScopes() {
        return scopes;
    }

    public void setScopes(Set<String> scopes) {
        this.scopes = scopes;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public Integer getAccessTokenDurationSecs() {
        return accessTokenDurationSecs;
    }

    public void setAccessTokenDurationSecs(Integer accessTokenDurationSecs) {
        this.accessTokenDurationSecs = accessTokenDurationSecs;
    }

    public Integer getRefreshTokenDurationSecs() {
        return refreshTokenDurationSecs;
    }

    public void setRefreshTokenDurationSecs(Integer refreshTokenDurationSecs) {
        this.refreshTokenDurationSecs = refreshTokenDurationSecs;
    }

    public static final class Builder {

        private String clientId;
        private String clientSecret;
        private Set<String> scopes;
        private String redirectUri;
        private Integer accessTokenDurationSecs;
        private Integer refreshTokenDurationSecs;

        public Builder withClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder withClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public Builder withScopes(Set<String> scopes) {
            this.scopes = scopes;
            return this;
        }

        public Builder withRedirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        public Builder withAccessTokenDurationSecs(Integer accessTokenDurationSecs) {
            this.accessTokenDurationSecs = accessTokenDurationSecs;
            return this;
        }

        public Builder withRefreshTokenDurationSecs(Integer refreshTokenDurationSecs) {
            this.refreshTokenDurationSecs = refreshTokenDurationSecs;
            return this;
        }

        public RestCreateClientRequest build() {
            return new RestCreateClientRequest(this);
        }

    }

}
