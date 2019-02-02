package com.pro.jenova.omnidrive.rest.controller.notification.request;

public class RestSendNotificationRequest {

    private String route;
    private String content;

    private RestSendNotificationRequest() {
        // REST
    }

    private RestSendNotificationRequest(Builder builder) {
        route = builder.route;
        content = builder.content;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static final class Builder {

        private String route;
        private String content;

        public Builder withRoute(String route) {
            this.route = route;
            return this;
        }

        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public RestSendNotificationRequest build() {
            return new RestSendNotificationRequest(this);
        }

    }

}
