package com.pro.jenova.justitia.rest.controller.login;

import com.pro.jenova.common.rest.ErrorResponse;
import com.pro.jenova.common.rest.RestResponse;
import com.pro.jenova.common.rest.VoidResponse;
import com.pro.jenova.justitia.data.entity.Login;
import com.pro.jenova.justitia.data.repository.ClientRepository;
import com.pro.jenova.justitia.data.repository.LoginRepository;
import com.pro.jenova.justitia.data.repository.UserRepository;
import com.pro.jenova.justitia.service.challenge.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.pro.jenova.common.util.IdUtils.uuid;
import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.MINUTES;

@RestController
@RequestMapping("/justitia-api/login")
public class LoginController {

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/init")
    public ResponseEntity<RestResponse> init(@RequestParam Map<String, String> requestParams) {
        Map<String, String> params = new HashMap<>(requestParams);

        String clientId = params.remove("clientId");
        String username = params.remove("username");

        if (clientId == null || username == null) {
            return ErrorResponse.badRequest("MISSING_MANDATORY_PARAMS");
        }

        if (!userRepository.existsByUsername(username) || !clientRepository.existsByClientId(clientId)) {
            return ErrorResponse.badRequest("MISSING_MANDATORY_PARAMS");
        }

        return init(clientId, username, params);
    }

    private ResponseEntity<RestResponse> init(String clientId, String username, Map<String, String> params) {
        loginRepository.removeByUsernameAndClientId(username, clientId);

        challengeService.evaluate(clientId, username, params).forEach(challenge -> {
            params.put(challenge.getKey(), challenge.getValue());
        });

        loginRepository.save(new Login.Builder()
                .withReference(uuid())
                .withClientId(clientId)
                .withUsername(username)
                .withParams(params)
                .withExpiresAt(tenMinutesFromNow())
                .build());

        return VoidResponse.created();
    }

    private LocalDateTime tenMinutesFromNow() {
        return now().plus(10, MINUTES);
    }

}
