package com.pro.jenova.justitia.rest.controller.client.response;

import java.util.Set;


public class RestClientDetails {

    private String clientId;
    private Set<String> scopes;
    private Set<String> redirectUri;
    private Integer accessTokenDurationSecs;
    private Integer refreshTokenDurationSecs;

    private RestClientDetails() {
        // REST
    }

    private RestClientDetails(Builder builder) {
        clientId = builder.clientId;
        scopes = builder.scopes;
        redirectUri = builder.redirectUri;
        accessTokenDurationSecs = builder.accessTokenDurationSecs;
        refreshTokenDurationSecs = builder.refreshTokenDurationSecs;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Set<String> getScopes() {
        return scopes;
    }

    public void setScopes(Set<String> scopes) {
        this.scopes = scopes;
    }

    public Set<String> getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(Set<String> redirectUri) {
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
        private Set<String> scopes;
        private Set<String> redirectUri;
        private Integer accessTokenDurationSecs;
        private Integer refreshTokenDurationSecs;

        public Builder withClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder withScopes(Set<String> scopes) {
            this.scopes = scopes;
            return this;
        }

        public Builder withRedirectUri(Set<String> redirectUri) {
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

        public RestClientDetails build() {
            return new RestClientDetails(this);
        }

    }

}
