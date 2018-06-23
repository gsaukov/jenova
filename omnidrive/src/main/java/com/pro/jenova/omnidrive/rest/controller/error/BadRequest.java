package com.pro.jenova.omnidrive.rest.controller.error;

import com.pro.jenova.omnidrive.rest.controller.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;
import static org.springframework.util.Assert.isTrue;

public class BadRequest extends ResponseEntity<BadRequest> implements RestResponse {

    private final Set<String> errorCodes;

    private BadRequest(Builder builder) {
        super(HttpStatus.BAD_REQUEST);

        errorCodes = unmodifiableSet(builder.errorCodes);
    }

    public static BadRequest dueTo(BindingResult violations) {
        isTrue(violations.hasErrors(), "illegal argument");

        Builder builder = new Builder();

        violations.getAllErrors().forEach(error -> builder.withErrorCode(error.getDefaultMessage()));

        return builder.build();
    }

    public Set<String> getErrorCodes() {
        return errorCodes;
    }

    public static final class Builder {

        private Set<String> errorCodes = new HashSet<>();

        public Builder withErrorCode(String errorCode) {
            this.errorCodes.add(errorCode);
            return this;
        }

        public BadRequest build() {
            return new BadRequest(this);
        }

    }

}
