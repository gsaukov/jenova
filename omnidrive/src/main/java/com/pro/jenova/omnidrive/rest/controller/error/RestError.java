package com.pro.jenova.omnidrive.rest.controller.error;

import com.pro.jenova.omnidrive.rest.controller.common.RestResponse;

public class RestError implements RestResponse {

    private String errorCode;
    private String message;

    private RestError() {
        // REST
    }

    private RestError(Builder builder) {
        errorCode = builder.errorCode;
        message = builder.message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static final class Builder {

        private String errorCode;
        private String message;

        public Builder withErrorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public RestError build() {
            return new RestError(this);
        }

    }

}
