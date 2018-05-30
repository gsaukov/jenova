package com.pro.jenova.omnidrive.rest.filter;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class RequestAndResponseFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(requestWrapper, responseWrapper);

        logRequest(request, requestWrapper);
        logResponse(response, responseWrapper);

        responseWrapper.copyBodyToResponse();
    }

    private void logRequest(HttpServletRequest request, ContentCachingRequestWrapper requestWrapper) {
        logger.debug("Method and URL - " + methodAndUrl(request));

        logContent(requestWrapper.getContentAsByteArray(), request.getCharacterEncoding());
    }

    private void logResponse(HttpServletResponse response, ContentCachingResponseWrapper responseWrapper) {
        logger.debug("Response Status - " + responseStatus(response));

        logContent(responseWrapper.getContentAsByteArray(), response.getCharacterEncoding());
    }

    private String methodAndUrl(HttpServletRequest request) {
        return request.getMethod().concat(" ").concat(request.getRequestURL().toString());
    }

    private String responseStatus(HttpServletResponse response) {
        return Integer.toString(response.getStatus());
    }

    private void logContent(byte[] content, String encoding) {
        String requestBody = getContentAsString(content, encoding);

        if (!requestBody.isEmpty()) {
            logger.debug(requestBody);
        }
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
