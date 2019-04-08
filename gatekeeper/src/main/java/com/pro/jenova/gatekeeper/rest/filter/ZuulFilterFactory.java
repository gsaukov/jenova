package com.pro.jenova.gatekeeper.rest.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


public class ZuulFilterFactory {


    public BearerHeaderFilter bearerHeaderFilter() {
        return new BearerHeaderFilter();
    }

}
