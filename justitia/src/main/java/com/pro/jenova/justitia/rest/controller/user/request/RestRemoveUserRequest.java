package com.pro.jenova.justitia.rest.controller.user.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RestRemoveUserRequest {

    @NotNull(message = "USERNAME_REQUIRED")
    @Size(min = 4, max = 64, message = "USERNAME_INVALID_SIZE")
    private String username;

    private RestRemoveUserRequest() {
        // REST
    }

    private RestRemoveUserRequest(Builder builder) {
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

        public RestRemoveUserRequest build() {
            return new RestRemoveUserRequest(this);
        }

    }

}
