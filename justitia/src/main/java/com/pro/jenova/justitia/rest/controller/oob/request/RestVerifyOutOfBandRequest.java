package com.pro.jenova.justitia.rest.controller.oob.request;

public class RestVerifyOutOfBandRequest {

    private String reference;

    private RestVerifyOutOfBandRequest() {
        // REST
    }

    private RestVerifyOutOfBandRequest(Builder builder) {
        reference = builder.reference;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public static final class Builder {

        private String reference;

        public Builder withReference(String reference) {
            this.reference = reference;
            return this;
        }

        public RestVerifyOutOfBandRequest build() {
            return new RestVerifyOutOfBandRequest(this);
        }

    }

}
