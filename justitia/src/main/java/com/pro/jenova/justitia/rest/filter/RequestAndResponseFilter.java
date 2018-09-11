package com.pro.jenova.justitia.rest.filter;

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
        logger.debug("Incoming Request - " + formattedForLogging(request));

        logContent(requestWrapper.getContentAsByteArray(), request.getCharacterEncoding());
    }

    private String formattedForLogging(HttpServletRequest request) {
        return request.getMethod().concat(" ").concat(request.getRequestURL().toString());
    }

    private void logResponse(HttpServletResponse response, ContentCachingResponseWrapper responseWrapper) {
        logger.debug("Outgoing Response - " + formattedForLogging(response));

        logContent(responseWrapper.getContentAsByteArray(), response.getCharacterEncoding());
    }

    private String formattedForLogging(HttpServletResponse response) {
        return Integer.toString(response.getStatus());
    }

    private void logContent(byte[] content, String encoding) {
        String requestBody = getContentAsString(content, encoding);

        if (!requestBody.isEmpty()) {
            logger.debug(requestBody);
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
