package com.pro.jenova.omnidrive.rest.controller.transfer;

import com.pro.jenova.common.rest.RestResponse;
import com.pro.jenova.common.rest.VoidResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/omnidrive-api/transfer")
public class TransferController {

    @GetMapping("/perform")
    public ResponseEntity<RestResponse> perform() {
        return VoidResponse.ok();
    }

}
