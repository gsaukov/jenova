package com.pro.jenova.omnidrive.rest.user;

public class RestUser {

    private final String username;
    private final String password;
    private final String email;

    private RestUser(Builder builder) {
        username = builder.username;
        password = builder.password;
        email = builder.email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public static final class Builder {

        private String username;
        private String password;
        private String email;

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public RestUser build() {
            return new RestUser(this);
        }

    }

}
