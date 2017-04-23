package com.pro.jenova.omnidrive.rest.controller.user;

import com.pro.jenova.omnidrive.rest.controller.common.RestResponse;

public class RestUser implements RestResponse {

    private String username;
    private String password;

    private RestUser() {
        // REST
    }

    private RestUser(Builder builder) {
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

        public RestUser build() {
            return new RestUser(this);
        }

    }

}
