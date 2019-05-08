package com.pro.jenova.omnidrive.rest.controller.eligibility;

import com.pro.jenova.common.rest.RestResponse;
import com.pro.jenova.common.rest.VoidResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/omnidrive-api/eligibility")
public class EligibilityController {

    @GetMapping("/check")
    public ResponseEntity<RestResponse> check() {
        return VoidResponse.ok();
    }

}
