package com.pro.jenova.justitia.rest.controller.login.response;

import com.pro.jenova.common.rest.RestResponse;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableMap;

public class RestInitLoginRequest implements RestResponse {

    private String username;
    private Map<String, String> attributes = emptyMap();

    private RestInitLoginRequest() {
        // REST
    }

    private RestInitLoginRequest(Builder builder) {
        username = builder.username;
        attributes = unmodifiableMap(builder.attributes);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public static final class Builder {

        private String username;
        private Map<String, String> attributes = new HashMap<>();

        private Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withAttribute(String name, String value) {
            this.attributes.put(name, value);
            return this;
        }

        public RestInitLoginRequest build() {
            return new RestInitLoginRequest(this);
        }

    }

}
