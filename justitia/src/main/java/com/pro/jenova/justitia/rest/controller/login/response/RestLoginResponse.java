package com.pro.jenova.justitia.rest.controller.login.response;

import com.pro.jenova.common.rest.RestResponse;

import java.util.Set;

public class RestLoginResponse implements RestResponse {

    private String reference;
    private Set<String> challenges;

    private RestLoginResponse() {
        // REST
    }

    private RestLoginResponse(Builder builder) {
        reference = builder.reference;
        challenges = builder.challenges;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Set<String> getChallenges() {
        return challenges;
    }

    public void setChallenges(Set<String> challenges) {
        this.challenges = challenges;
    }

    public static final class Builder {

        private String reference;
        private Set<String> challenges;

        public Builder withReference(String reference) {
            this.reference = reference;
            return this;
        }

        public Builder withChallenges(Set<String> challenges) {
            this.challenges = challenges;
            return this;
        }

        public RestLoginResponse build() {
            return new RestLoginResponse(this);
        }

    }

}
