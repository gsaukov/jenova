package com.pro.jenova.common.rest.filter;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static java.lang.System.lineSeparator;
import static java.util.Collections.list;

public class RequestAndResponseFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(requestWrapper, responseWrapper);

        logRequest(request, requestWrapper);
        logResponse(response, responseWrapper);

        responseWrapper.copyBodyToResponse();
    }

    private void logRequest(HttpServletRequest request, ContentCachingRequestWrapper requestWrapper) {
        StringBuilder builder = new StringBuilder();

        logHeaders(request, builder);
        logContent(requestWrapper.getContentAsByteArray(), request.getCharacterEncoding(), builder);

        logger.debug(builder.toString());
    }

    private void logHeaders(HttpServletRequest request, StringBuilder builder) {
        builder.append("Incoming Request - ")
                .append(request.getMethod()).append(" ")
                .append(request.getRequestURL().toString())
                .append(lineSeparator());

        list(request.getHeaderNames()).forEach(header -> logHeader(header, request, builder));
    }

    private void logHeader(String headerName, HttpServletRequest request, StringBuilder builder) {
        builder.append(headerName).append(" = ").append(request.getHeader(headerName)).append(lineSeparator());
    }

    private void logResponse(HttpServletResponse response, ContentCachingResponseWrapper responseWrapper) {
        StringBuilder builder = new StringBuilder();

        logHeaders(response, builder);
        logContent(responseWrapper.getContentAsByteArray(), response.getCharacterEncoding(), builder);

        logger.debug(builder.toString());
    }

    private void logHeaders(HttpServletResponse response, StringBuilder builder) {
        builder.append("Outgoing Response - ")
                .append(response.getStatus())
                .append(lineSeparator());

        response.getHeaderNames().forEach(header -> logHeader(header, response, builder));
    }

    private void logHeader(String headerName, HttpServletResponse response, StringBuilder builder) {
        builder.append(headerName).append(" = ").append(response.getHeader(headerName)).append(lineSeparator());
    }

    private void logContent(byte[] content, String encoding, StringBuilder builder) {
        String requestBody = getContentAsString(content, encoding);

        if (!requestBody.isEmpty()) {
            builder.append(requestBody).append(lineSeparator());
        }
    }

    private String getContentAsString(byte[] buffer, String encoding) {
        if (buffer == null || buffer.length == 0) {
            return "";
        }

        if (encoding == null) {
            return "Unspecified Encoding";
        }

        try {
            return new String(buffer, 0, buffer.length, encoding);
        } catch (final UnsupportedEncodingException exc) {
            return "Unsupported Encoding";
        }
    }

}
