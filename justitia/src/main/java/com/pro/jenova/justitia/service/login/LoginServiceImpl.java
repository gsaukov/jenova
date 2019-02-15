package com.pro.jenova.justitia.service.login;

import com.pro.jenova.justitia.data.entity.LoginRequest;
import com.pro.jenova.justitia.data.entity.LoginVerification;
import com.pro.jenova.justitia.data.repository.LoginRequestRepository;
import com.pro.jenova.justitia.data.repository.LoginVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.stream.Stream;

import static com.pro.jenova.common.util.IdUtils.uuid;
import static com.pro.jenova.justitia.data.entity.LoginVerification.Method.ONE_TIME_PASSWORD;
import static com.pro.jenova.justitia.data.entity.LoginVerification.Method.USERNAME_PASSWORD;
import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginRequestRepository loginRequestRepository;

    @Autowired
    private LoginVerificationRepository loginVerificationRepository;

    @Override
    public LoginServiceResult initiate(String username, Map<String, String> attributes) {
        LoginRequest loginRequest = saveLoginRequest(username, attributes);

        Stream<LoginVerification.Method> methods = of(USERNAME_PASSWORD, ONE_TIME_PASSWORD);

        methods.forEach(method -> saveLoginVerification(loginRequest, method));

        return new LoginServiceResult.Builder()
                .withReference(loginRequest.getReference())
                .withVerifications(methods.collect(toList()))
                .build();
    }

    private LoginVerification saveLoginVerification(LoginRequest loginRequest, LoginVerification.Method method) {
        return loginVerificationRepository.save(new LoginVerification.Builder()
                .withLoginRequest(loginRequest)
                .withMethod(method)
                .withStatus(LoginVerification.Status.PENDING)
                .build());
    }

    private LoginRequest saveLoginRequest(String username, Map<String, String> attributes) {
        LoginRequest.Builder builder = new LoginRequest.Builder()
                .withUsername(username)
                .withReference(uuid())
                .withStatus(LoginRequest.Status.PENDING)
                .withExpiresAt(now().plusMinutes(10));

        attributes.forEach((key, value) -> builder.withAttribute(key, value));

        return loginRequestRepository.save(builder.build());
    }

}
