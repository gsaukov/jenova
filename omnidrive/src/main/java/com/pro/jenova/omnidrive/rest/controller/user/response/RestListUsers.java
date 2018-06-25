package com.pro.jenova.omnidrive.rest.controller.user.response;

import com.pro.jenova.omnidrive.rest.controller.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class RestListUsers extends ResponseEntity<RestListUsers> implements RestResponse {

    private List<String> usernames;

    private RestListUsers() {
        // REST
        super(HttpStatus.OK);
    }

    private RestListUsers(Builder builder) {
        this();
        usernames = unmodifiableList(builder.usernames);
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }

    public static final class Builder {

        private List<String> usernames = new ArrayList<>();

        public Builder withUsername(String username) {
            this.usernames.add(username);
            return this;
        }

        public Builder withUsernames(Collection<String> usernames) {
            this.usernames.addAll(usernames);
            return this;
        }

        public RestListUsers build() {
            return new RestListUsers(this);
        }

    }

}
