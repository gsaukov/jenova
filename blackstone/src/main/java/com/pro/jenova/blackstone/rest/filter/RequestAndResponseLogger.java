package com.pro.jenova.blackstone.rest.filter;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class RequestAndResponseLogger extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.debug("Method and URL - " + methodAndUrl(request));

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(requestWrapper, responseWrapper);

        String requestBody = getContentAsString(requestWrapper.getContentAsByteArray(), request.getCharacterEncoding());

        if (!requestBody.isEmpty()) {
            logger.debug(requestBody);
        }

        logger.debug("Response Status - " + responseStatus(response));

        String responseBody = getContentAsString(responseWrapper.getContentAsByteArray(), response.getCharacterEncoding());

        if (!responseBody.isEmpty()) {
            logger.debug(responseBody);
        }

        responseWrapper.copyBodyToResponse();
    }

    private String methodAndUrl(HttpServletRequest request) {
        return request.getMethod().concat(" ").concat(request.getRequestURL().toString());
    }

    private String responseStatus(HttpServletResponse response) {
        return Integer.toString(response.getStatus());
    }

    private String getContentAsString(byte[] buffer, String charsetName) {
        if (buffer == null || buffer.length == 0) {
            return "";
        }

        try {
            return new String(buffer, 0, buffer.length, charsetName);
        } catch (final UnsupportedEncodingException exc) {
            return "Unsupported Encoding";
        }
    }

}
