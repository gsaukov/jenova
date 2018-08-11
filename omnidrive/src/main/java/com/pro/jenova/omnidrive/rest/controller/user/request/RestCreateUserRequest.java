package com.pro.jenova.omnidrive.rest.controller.user.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RestCreateUserRequest {

    @NotNull(message = "USERNAME_REQUIRED")
    @Size(min = 8, max = 64, message = "USERNAME_INVALID_SIZE")
    private String username;

    @NotNull(message = "PASSWORD_REQUIRED")
    @Size(min = 8, max = 64, message = "PASSWORD_INVALID_SIZE")
    private String password;

    private RestCreateUserRequest() {
        // REST
    }

    private RestCreateUserRequest(Builder builder) {
        username = builder.username;
        password = builder.password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static final class Builder {

        private String username;
        private String password;

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public RestCreateUserRequest build() {
            return new RestCreateUserRequest(this);
        }

    }

}
