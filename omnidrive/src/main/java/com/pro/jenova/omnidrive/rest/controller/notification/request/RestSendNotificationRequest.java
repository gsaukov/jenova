package com.pro.jenova.omnidrive.rest.controller.notification.request;

public class RestSendNotificationRequest {

    private String content;

    private RestSendNotificationRequest() {
        // REST
    }

    private RestSendNotificationRequest(Builder builder) {
        content = builder.content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static final class Builder {

        private String content;
        
        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public RestSendNotificationRequest build() {
            return new RestSendNotificationRequest(this);
        }

    }

}
