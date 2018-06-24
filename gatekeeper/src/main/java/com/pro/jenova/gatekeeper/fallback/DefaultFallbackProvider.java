package com.pro.jenova.gatekeeper.fallback;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Configuration
public class DefaultFallbackProvider {

    @Bean
    public FallbackProvider defaultFallbackProvider() {
        return new FallbackProvider() {

            @Override
            public String getRoute() {
                // The serviceId (not the route).
                return "*";
            }

            @Override
            public ClientHttpResponse fallbackResponse() {
                return defaultResponse();
            }

            @Override
            public ClientHttpResponse fallbackResponse(Throwable cause) {
                return defaultResponse();
            }

            private ClientHttpResponse defaultResponse() {
                return new ClientHttpResponse() {
                    @Override
                    public HttpStatus getStatusCode() {
                        return HttpStatus.OK;
                    }

                    @Override
                    public int getRawStatusCode() {
                        return HttpStatus.OK.value();
                    }

                    @Override
                    public String getStatusText() {
                        return HttpStatus.OK.toString();
                    }

                    @Override
                    public void close() {
                    }

                    @Override
                    public InputStream getBody() {
                        return new ByteArrayInputStream("{\"message\":\"Sorry, the requested service is down\"}".getBytes());
                    }

                    @Override
                    public HttpHeaders getHeaders() {
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        return headers;
                    }

                };

            }

        };

    }

}
