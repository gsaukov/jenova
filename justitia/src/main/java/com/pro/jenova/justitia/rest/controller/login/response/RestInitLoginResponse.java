package com.pro.jenova.justitia.rest.controller.login.response;

import com.pro.jenova.common.rest.RestResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class RestInitLoginResponse implements RestResponse {

    private String reference;
    private List<String> verifications;

    private RestInitLoginResponse() {
        // REST
    }

    private RestInitLoginResponse(Builder builder) {
        reference = builder.reference;
        verifications = unmodifiableList(builder.verifications);
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public List<String> getVerifications() {
        return verifications;
    }

    public void setVerifications(List<String> verifications) {
        this.verifications = verifications;
    }

    public static final class Builder {

        private String reference;
        private List<String> verifications = new ArrayList<>();

        public Builder withReference(String reference) {
            this.reference = reference;
            return this;
        }

        public Builder withVerification(String verification) {
            this.verifications.add(verification);
            return this;
        }

        public Builder withVerifications(Collection<String> verifications) {
            this.verifications.addAll(verifications);
            return this;
        }

        public RestInitLoginResponse build() {
            return new RestInitLoginResponse(this);
        }

    }

}
