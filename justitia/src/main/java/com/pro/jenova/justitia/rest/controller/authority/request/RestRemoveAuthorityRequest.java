package com.pro.jenova.justitia.rest.controller.authority.request;

public class RestRemoveAuthorityRequest {

    private String username;
    private String authority;

    private RestRemoveAuthorityRequest() {
        // REST
    }

    private RestRemoveAuthorityRequest(Builder builder) {
        username = builder.username;
        authority = builder.authority;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public static final class Builder {

        private String username;
        private String authority;

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withAuthority(String authority) {
            this.authority = authority;
            return this;
        }

        public RestRemoveAuthorityRequest build() {
            return new RestRemoveAuthorityRequest(this);
        }

    }

}
