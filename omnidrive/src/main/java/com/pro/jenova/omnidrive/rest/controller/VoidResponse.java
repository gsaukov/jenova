package com.pro.jenova.omnidrive.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class VoidResponse extends ResponseEntity<Void> implements RestResponse {

    public static final VoidResponse CREATED = new VoidResponse(HttpStatus.CREATED);

    public VoidResponse(HttpStatus httpStatus) {
        super(httpStatus);
    }

}
