package com.pro.jenova.justitia.rest.controller.scope.response;

public class RestListScopeDetails {

    private String clientId;
    private String name;
    private String permissions;

    private RestListScopeDetails() {
        // REST
    }

    private RestListScopeDetails(Builder builder) {
        clientId = builder.clientId;
        name = builder.name;
        permissions = builder.permissions;
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

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public static final class Builder {

        private String clientId;
        private String name;
        private String permissions;

        public Builder withClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withPermissions(String permissions) {
            this.permissions = permissions;
            return this;
        }

        public RestListScopeDetails build() {
            return new RestListScopeDetails(this);
        }

    }

}
