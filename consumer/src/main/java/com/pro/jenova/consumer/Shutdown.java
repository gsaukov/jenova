package com.pro.jenova.consumer;

import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class Shutdown {

    public static void main(String[] args) {
        try {
            shutdown(args);
        } catch (final ResourceAccessException exc) {
            System.out.println(exc.getMessage());
        }
    }

    private static void shutdown(String[] args) {
        new RestTemplate().postForEntity(
                args[0].concat("/shutdown"), new HttpEntity<>(new HashMap(), standardHeaders()), String.class);
    }

    private static LinkedMultiValueMap<String, String> standardHeaders() {
        Map headers = new HashMap<String, String>();

        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");

        LinkedMultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();

        multiValueMap.setAll(headers);

        return multiValueMap;
    }

}
