package com.pro.jenova.justitia.rest.controller.oob;

import com.pro.jenova.common.rest.ErrorResponse;
import com.pro.jenova.common.rest.RestResponse;
import com.pro.jenova.common.rest.VoidResponse;
import com.pro.jenova.justitia.service.challenge.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.pro.jenova.justitia.data.entity.Challenge.Type.OUT_OF_BAND;

@RestController
@RequestMapping("/justitia-api/out-of-band")
public class OutOfBandController {

    @Autowired
    private ChallengeService challengeService;

    @PostMapping("/notify")
    public ResponseEntity<RestResponse> notify(@RequestParam String reference) {
        boolean isSuccess = challengeService.complete(reference, OUT_OF_BAND);

        if (isSuccess) {
            return VoidResponse.ok();
        }

        return ErrorResponse.badRequest("OUT_OF_BAND_INCONSISTENT");
    }

}
