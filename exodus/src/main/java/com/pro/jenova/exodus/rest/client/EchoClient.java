package com.pro.jenova.exodus.rest.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// FIXME
@FeignClient("omnidrive")
public interface EchoClient {

    @RequestMapping(method = RequestMethod.GET, value = "/echo/{times}",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    String echo(@PathVariable(value = "times") int times);

}