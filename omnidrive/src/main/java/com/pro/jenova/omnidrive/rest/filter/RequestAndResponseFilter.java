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
import java.util.HashSet;
import java.util.Set;

import static java.text.MessageFormat.format;
import static java.util.Arrays.asList;

public class RequestAndResponseFilter extends OncePerRequestFilter {

    private static final Set<String> LOGGABLE_CONTENT_TYPES = new HashSet<>(asList("application/xml", "application/json"));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        logRequest(request, requestWrapper);

        filterChain.doFilter(requestWrapper, responseWrapper);

        logResponse(response, responseWrapper);

        responseWrapper.copyBodyToResponse();
    }

    private void logRequest(HttpServletRequest request, ContentCachingRequestWrapper requestWrapper) {
        logger.debug("Method and URL - " + methodAndUrl(request));

        logContent(requestWrapper.getContentAsByteArray(), request.getCharacterEncoding(), request.getContentType());
    }

    private void logResponse(HttpServletResponse response, ContentCachingResponseWrapper responseWrapper) {
        logger.debug("Response Status - " + responseStatus(response));

        logContent(responseWrapper.getContentAsByteArray(), response.getCharacterEncoding(), response.getContentType());
    }

    private String methodAndUrl(HttpServletRequest request) {
        return request.getMethod().concat(" ").concat(request.getRequestURL().toString());
    }

    private String responseStatus(HttpServletResponse response) {
        return Integer.toString(response.getStatus());
    }

    private void logContent(byte[] content, String enconding, String contentType) {
        if (!LOGGABLE_CONTENT_TYPES.contains(contentType)) {
            logger.debug(format("Unsupported Content-Type [{0}]", contentType));
            return;
        }

        String requestBody = getContentAsString(content, enconding);

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
