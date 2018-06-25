package com.pro.jenova.omnidrive.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class VoidResponse implements RestResponse {

    public static ResponseEntity<RestResponse> created() {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
