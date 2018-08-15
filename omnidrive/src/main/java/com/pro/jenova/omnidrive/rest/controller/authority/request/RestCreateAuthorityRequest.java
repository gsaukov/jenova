package com.pro.jenova.omnidrive.rest.controller.authority.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RestCreateAuthorityRequest {

    @NotNull(message = "USERNAME_REQUIRED")
    @Size(min = 4, max = 64, message = "USERNAME_INVALID_SIZE")
    private String username;

    @NotNull(message = "AUTHORITY_REQUIRED")
    @Size(min = 4, max = 64, message = "AUTHORITY_INVALID_SIZE")
    private String authority;

    private RestCreateAuthorityRequest() {
        // REST
    }

    private RestCreateAuthorityRequest(Builder builder) {
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

        public RestCreateAuthorityRequest build() {
            return new RestCreateAuthorityRequest(this);
        }

    }

}
