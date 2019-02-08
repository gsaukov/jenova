package com.pro.jenova.justitia.rest.controller.client.response;

import com.pro.jenova.common.rest.RestResponse;

import java.util.List;


public class RestListClientsResponse implements RestResponse {

    private List<RestClientDetails> clients;

    private RestListClientsResponse() {
        // REST
    }

    private RestListClientsResponse(Builder builder) {
        clients = builder.clients;
    }

    public List<RestClientDetails> getClients() {
        return clients;
    }

    public void ListClients(List<RestClientDetails> clients) {
        this.clients = clients;
    }

    public static final class Builder {

        private List<RestClientDetails> clients;

        public Builder withClients(List<RestClientDetails> clients) {
            this.clients = clients;
            return this;
        }

        public RestListClientsResponse build() {
            return new RestListClientsResponse(this);
        }

    }

}
