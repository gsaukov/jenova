package com.pro.jenova.omnidrive.rest.controller.authority.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RestListAuthoritiesRequest {

    @NotNull(message = "USERNAME_REQUIRED")
    @Size(min = 4, max = 64, message = "USERNAME_INVALID_SIZE")
    private String username;

    private RestListAuthoritiesRequest() {
        // REST
    }

    private RestListAuthoritiesRequest(Builder builder) {
        username = builder.username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static final class Builder {

        private String username;

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public RestListAuthoritiesRequest build() {
            return new RestListAuthoritiesRequest(this);
        }

    }

}
