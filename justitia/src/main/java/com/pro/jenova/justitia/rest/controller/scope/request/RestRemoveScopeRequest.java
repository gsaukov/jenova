package com.pro.jenova.justitia.rest.controller.scope.request;

public class RestRemoveScopeRequest {

    private String clientId;
    private String name;

    private RestRemoveScopeRequest() {
        // REST
    }

    private RestRemoveScopeRequest(Builder builder) {
        clientId = builder.clientId;
        name = builder.name;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static final class Builder {

        private String clientId;
        private String name;

        public Builder withClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public RestRemoveScopeRequest build() {
            return new RestRemoveScopeRequest(this);
        }

    }

}
