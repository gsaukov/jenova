package com.pro.jenova.justitia.security;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {

    private static final String REFERENCE = "reference";
    private static final String ONE_TIME_PASSWORD = "one_time_password";

    private String reference;
    private String oneTimePassword;

    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);

        reference = request.getParameter(REFERENCE);
        oneTimePassword = request.getParameter(ONE_TIME_PASSWORD);
    }

    public String getReference() {
        return reference;
    }

    public String getOneTimePassword() {
        return oneTimePassword;
    }

}
