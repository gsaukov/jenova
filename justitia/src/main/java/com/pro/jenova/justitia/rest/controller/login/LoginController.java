package com.pro.jenova.justitia.rest.controller.login;

import com.pro.jenova.common.rest.ErrorResponse;
import com.pro.jenova.common.rest.RestResponse;
import com.pro.jenova.justitia.data.entity.Login;
import com.pro.jenova.justitia.data.repository.ClientRepository;
import com.pro.jenova.justitia.data.repository.LoginRepository;
import com.pro.jenova.justitia.data.repository.UserRepository;
import com.pro.jenova.justitia.rest.controller.login.response.RestLoginResponse;
import com.pro.jenova.justitia.service.challenge.Challenge;
import com.pro.jenova.justitia.service.challenge.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.pro.jenova.common.util.IdUtils.uuid;
import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.MINUTES;
import static org.springframework.util.Assert.isTrue;

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
    public ResponseEntity<RestResponse> init(HttpServletRequest request) {
        Map<String, String> params = convert(request.getParameterMap());

        String clientId = params.get("clientId");
        String username = params.get("username");

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

        Map<String, String> challenges = challengeService.evaluate(clientId, username, params).stream()
                .collect(Collectors.toMap(Challenge::getKey, Challenge::getValue));

        String reference = uuid();

        loginRepository.save(new Login.Builder()
                .withReference(reference)
                .withClientId(clientId)
                .withUsername(username)
                .withParams(params)
                .withParams(challenges)
                .withExpiresAt(tenMinutesFromNow())
                .build());

        return new ResponseEntity<>(new RestLoginResponse.Builder()
                .withReference(reference)
                .withChallenges(challenges.keySet())
                .withProvidedParams(params)
                .build(), HttpStatus.OK);
    }

    private LocalDateTime tenMinutesFromNow() {
        return now().plus(10, MINUTES);
    }

    private Map<String, String> convert(Map<String, String[]> params) {
        Map<String, String> convertedParams = new HashMap<>();

        params.entrySet().forEach(entry -> {
            String[] values = entry.getValue();
            isTrue(values.length == 1, "single values expected");
            convertedParams.put(entry.getKey(), values[0]);
        });

        return convertedParams;
    }

}
