package com.pro.jenova.omnidrive.rest.controller.error;

import com.pro.jenova.omnidrive.rest.controller.RestResponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.springframework.util.Assert.isTrue;

public class RestErrors implements RestResponse {

    private List<RestError> errors;

    private RestErrors() {
        // REST
    }

    private RestErrors(Builder builder) {
        errors = builder.errors;
    }

    public List<RestError> getErrors() {
        return errors;
    }

    public void setErrors(List<RestError> errors) {
        this.errors = errors;
    }

    public static final RestErrors from(BindingResult violations) {
        isTrue(violations.hasErrors(), "must have errors");

        RestErrors.Builder builder = new RestErrors.Builder();

        for (final ObjectError violation : violations.getAllErrors()) {
            builder.withError(violation.getDefaultMessage(), violation.getCode());
        }

        return builder.build();
    }

    public static final class Builder {

        private List<RestError> errors = emptyList();

        public Builder withError(String errorCode, String message) {
            if (errors.isEmpty()) {
                errors = new ArrayList<>();
            }

            errors.add(new RestError.Builder()
                    .withErrorCode(errorCode)
                    .withMessage(message)
                    .build());

            return this;
        }

        public RestErrors build() {
            return new RestErrors(this);
        }

    }

}
