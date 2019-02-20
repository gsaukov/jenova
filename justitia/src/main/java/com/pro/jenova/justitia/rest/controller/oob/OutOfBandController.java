package com.pro.jenova.justitia.rest.controller.oob;

import com.pro.jenova.common.rest.RestResponse;
import com.pro.jenova.common.rest.VoidResponse;
import com.pro.jenova.justitia.rest.controller.oob.request.RestVerifyOutOfBandRequest;
import com.pro.jenova.justitia.service.oob.OutOfBandRegisterer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/justitia-api/oob")
public class OutOfBandController {

    @Autowired
    private OutOfBandRegisterer outOfBandRegisterer;

    @PostMapping("/verify")
    public ResponseEntity<RestResponse> verify(@RequestBody RestVerifyOutOfBandRequest restVerifyOutOfBandRequest) {
        outOfBandRegisterer.register(restVerifyOutOfBandRequest.getReference());

        return VoidResponse.ok();
    }

}
