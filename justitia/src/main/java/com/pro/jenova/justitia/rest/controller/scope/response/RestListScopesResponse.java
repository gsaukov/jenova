package com.pro.jenova.justitia.rest.controller.scope.response;

import com.pro.jenova.common.rest.RestResponse;

import java.util.List;

public class RestListScopesResponse implements RestResponse {

    private List<RestListScopeDetails> scopes;

    private RestListScopesResponse() {
        // REST
    }

    private RestListScopesResponse(RestListScopesResponse.Builder builder) {
        scopes = builder.scopes;
    }

    public List<RestListScopeDetails> getScopes() {
        return scopes;
    }

    public void setScopes(List<RestListScopeDetails> scopes) {
        this.scopes = scopes;
    }

    public static final class Builder {

        private List<RestListScopeDetails> scopes;

        public RestListScopesResponse.Builder withScopes(List<RestListScopeDetails> scopes) {
            this.scopes = scopes;
            return this;
        }

        public RestListScopesResponse build() {
            return new RestListScopesResponse(this);
        }

    }

}
