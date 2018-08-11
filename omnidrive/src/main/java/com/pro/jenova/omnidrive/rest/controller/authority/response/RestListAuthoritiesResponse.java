package com.pro.jenova.omnidrive.rest.controller.authority.response;

import com.pro.jenova.omnidrive.rest.controller.RestResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class RestListAuthoritiesResponse implements RestResponse {

    private List<String> authorities;

    private RestListAuthoritiesResponse() {
        // REST
    }

    private RestListAuthoritiesResponse(Builder builder) {
        authorities = unmodifiableList(builder.authorities);
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public static final class Builder {

        private List<String> authorities = new ArrayList<>();

        public Builder withAuthority(String authority) {
            this.authorities.add(authority);
            return this;
        }

        public Builder withAuthorities(Collection<String> authorities) {
            this.authorities.addAll(authorities);
            return this;
        }

        public RestListAuthoritiesResponse build() {
            return new RestListAuthoritiesResponse(this);
        }

    }

}
