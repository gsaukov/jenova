package com.pro.jenova.justitia.service.login;

import com.pro.jenova.common.rest.RestResponse;
import com.pro.jenova.justitia.data.entity.LoginVerification.Method;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class LoginServiceResult implements RestResponse {

    private String reference;
    private List<Method> verifications;

    private LoginServiceResult() {
        // No Reason
    }

    private LoginServiceResult(Builder builder) {
        reference = builder.reference;
        verifications = unmodifiableList(builder.verifications);
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public List<Method> getVerifications() {
        return verifications;
    }

    public void setVerifications(List<Method> verifications) {
        this.verifications = verifications;
    }

    public static final class Builder {

        private String reference;
        private List<Method> verifications = new ArrayList<>();

        public Builder withReference(String reference) {
            this.reference = reference;
            return this;
        }

        public Builder withVerification(Method verification) {
            this.verifications.add(verification);
            return this;
        }

        public Builder withVerifications(Collection<Method> verifications) {
            this.verifications.addAll(verifications);
            return this;
        }

        public LoginServiceResult build() {
            return new LoginServiceResult(this);
        }

    }

}
