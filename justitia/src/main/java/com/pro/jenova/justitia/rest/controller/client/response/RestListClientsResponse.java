package com.pro.jenova.justitia.rest.controller.client.response;

import com.pro.jenova.common.rest.RestResponse;

import java.util.List;

import static java.util.Collections.unmodifiableList;

public class RestListClientsResponse implements RestResponse {

    private List<RestListClientDetails> clients;

    private RestListClientsResponse() {
        // REST
    }

    private RestListClientsResponse(Builder builder) {
        clients = unmodifiableList(builder.clients);
    }

    public List<RestListClientDetails> getClients() {
        return clients;
    }

    public void ListClients(List<RestListClientDetails> clients) {
        this.clients = clients;
    }

    public static final class Builder {

        private List<RestListClientDetails> clients;

        public Builder withClients(List<RestListClientDetails> clients) {
            this.clients = clients;
            return this;
        }

        public RestListClientsResponse build() {
            return new RestListClientsResponse(this);
        }

    }

}
