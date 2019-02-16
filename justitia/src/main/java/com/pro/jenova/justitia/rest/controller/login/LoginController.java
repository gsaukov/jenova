package com.pro.jenova.justitia.rest.controller.login;

import com.pro.jenova.common.rest.RestResponse;
import com.pro.jenova.justitia.data.entity.LoginVerification.Method;
import com.pro.jenova.justitia.data.repository.UserRepository;
import com.pro.jenova.justitia.rest.controller.login.response.RestInitLoginRequest;
import com.pro.jenova.justitia.rest.controller.login.response.RestInitLoginResponse;
import com.pro.jenova.justitia.service.login.LoginService;
import com.pro.jenova.justitia.service.login.LoginServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.pro.jenova.common.util.IdUtils.uuid;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/initiate")
    public ResponseEntity<RestResponse> initiate(@RequestBody RestInitLoginRequest restInitLoginRequest) {
        if (!userRepository.existsByUsername(restInitLoginRequest.getUsername())) {
            // Return a dummy valid response to avoid attackers from guessing users.
            return new ResponseEntity<>(new RestInitLoginResponse.Builder().withReference(uuid())
                    .withVerifications(asList(Method.USERNAME_PASSWORD.toString())).build(), HttpStatus.OK);
        }

        LoginServiceResult result = loginService.initiate(
                restInitLoginRequest.getUsername(), restInitLoginRequest.getAttributes());

        return new ResponseEntity<>(new RestInitLoginResponse.Builder().withReference(result.getReference())
                .withVerifications(result.getVerifications().stream().map(Method::name).collect(toList())).build(),
                HttpStatus.OK);
    }

}
