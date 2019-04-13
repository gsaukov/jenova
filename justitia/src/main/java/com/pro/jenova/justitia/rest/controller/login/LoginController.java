package com.pro.jenova.justitia.rest.controller.login;

import com.pro.jenova.common.rest.ErrorResponse;
import com.pro.jenova.common.rest.RestResponse;
import com.pro.jenova.justitia.rest.controller.login.response.RestLoginResponse;
import com.pro.jenova.justitia.service.login.LoginResult;
import com.pro.jenova.justitia.service.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.springframework.util.Assert.isTrue;

@RestController
@RequestMapping("/justitia-api/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/init")
    public ResponseEntity<RestResponse> init(HttpServletRequest request) {
        Map<String, String> params = convert(request.getParameterMap());

        String clientId = params.remove("clientId");
        String username = params.remove("username");
        String scopes = params.remove("scopes");

        if (clientId == null || username == null || scopes == null) {
            return ErrorResponse.badRequest("MISSING_MANDATORY_PARAMS");
        }

        return init(clientId, username, scopes, params);
    }

    private ResponseEntity<RestResponse> init(String clientId, String username, String scopes,
                                              Map<String, String> params) {
        LoginResult result = loginService.init(clientId, username, scopes, params);

        Set<String> challenges = result.getChallenges().stream()
                .map(challenge -> challenge.getType().name())
                .collect(toSet());

        return new ResponseEntity<>(new RestLoginResponse.Builder()
                .withReference(result.getLogin().getReference())
                .withChallenges(challenges)
                .build(), HttpStatus.OK);
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
