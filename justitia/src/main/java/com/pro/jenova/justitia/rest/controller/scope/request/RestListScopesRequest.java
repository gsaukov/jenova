package com.pro.jenova.justitia.rest.controller.scope.request;

public class RestListScopesRequest {

    private String clientId;

    private RestListScopesRequest() {
        // REST
    }

    private RestListScopesRequest(Builder builder) {
        clientId = builder.clientId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public static final class Builder {

        private String clientId;

        public Builder withClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public RestListScopesRequest build() {
            return new RestListScopesRequest(this);
        }

    }

}
