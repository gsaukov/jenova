package com.pro.jenova.justitia.rest.filter;

import com.pro.jenova.justitia.service.otp.OneTimePasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OneTimePasswordFilter extends OncePerRequestFilter {

    public static final String ONE_TIME_PASSWORD = "one_time_password";
    public static final String REFERENCE = "reference";

    @Autowired
    private OneTimePasswordService oneTimePasswordService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        register(request);

        filterChain.doFilter(request, response);
    }

    private void register(HttpServletRequest request) {
        String reference = request.getHeader(REFERENCE);

        if (reference == null) {
            return;
        }

        String oneTimePassword = request.getHeader(ONE_TIME_PASSWORD);

        if (oneTimePassword == null) {
            return;
        }

        oneTimePasswordService.register(reference, oneTimePassword);
    }

}
