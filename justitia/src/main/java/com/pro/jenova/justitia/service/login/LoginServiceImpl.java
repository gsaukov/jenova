package com.pro.jenova.justitia.service.login;

import com.pro.jenova.justitia.data.entity.LoginRequest;
import com.pro.jenova.justitia.data.entity.LoginVerification;
import com.pro.jenova.justitia.data.repository.LoginRequestRepository;
import com.pro.jenova.justitia.data.repository.LoginVerificationRepository;
import com.pro.jenova.justitia.messaging.otp.OneTimePasswordProducer;
import com.pro.jenova.justitia.service.otp.OneTimePasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.pro.jenova.common.util.IdUtils.uuid;
import static java.time.LocalDateTime.now;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    private static final String LEVEL_1 = "level_1";
    private static final String LEVEL_2 = "level_2";

    @Autowired
    private LoginRequestRepository loginRequestRepository;

    @Autowired
    private LoginVerificationRepository loginVerificationRepository;

    @Autowired
    private OneTimePasswordGenerator oneTimePasswordGenerator;

    @Autowired
    private OneTimePasswordProducer oneTimePasswordProducer;

    @Override
    public LoginServiceResult initiate(String username, String level, Map<String, String> attributes) {
        LoginRequest loginRequest = saveLoginRequest(username, level, attributes);

        List<LoginVerification.Method> methods = saveLoginVerificationMethods(loginRequest, level);

        return new LoginServiceResult.Builder()
                .withReference(loginRequest.getReference())
                .withVerifications(methods)
                .build();
    }

    private List<LoginVerification.Method> saveLoginVerificationMethods(LoginRequest loginRequest, String level) {
        List<LoginVerification.Method> methods = new ArrayList<>();

        if (LEVEL_1.equals(level)) {
            saveUsernamePassword(loginRequest, methods);
            saveOneTimePassword(loginRequest, methods);
        } else if (LEVEL_2.equals(level)) {
            saveOutOfBand(loginRequest, methods);
        } else {
            throw new IllegalArgumentException("invalid level specified " + level);
        }

        return methods;
    }

    private void saveOutOfBand(LoginRequest loginRequest, List<LoginVerification.Method> methods) {
        loginVerificationRepository.save(new LoginVerification.Builder()
                .withLoginRequest(loginRequest)
                .withMethod(LoginVerification.Method.OUT_OF_BAND)
                .withStatus(LoginVerification.Status.PENDING)
                .build());

        methods.add(LoginVerification.Method.OUT_OF_BAND);
    }

    private void saveOneTimePassword(LoginRequest loginRequest, List<LoginVerification.Method> methods) {
        String oneTimePassword = oneTimePasswordGenerator.generate();

        loginVerificationRepository.save(new LoginVerification.Builder()
                .withLoginRequest(loginRequest)
                .withMethod(LoginVerification.Method.ONE_TIME_PASSWORD)
                .withStatus(LoginVerification.Status.PENDING)
                .withValue(oneTimePassword)
                .build());

        oneTimePasswordProducer.sendOneTimePassword(loginRequest.getUsername(), oneTimePassword);

        methods.add(LoginVerification.Method.ONE_TIME_PASSWORD);
    }

    private void saveUsernamePassword(LoginRequest loginRequest, List<LoginVerification.Method> methods) {
        loginVerificationRepository.save(new LoginVerification.Builder()
                .withLoginRequest(loginRequest)
                .withMethod(LoginVerification.Method.USERNAME_PASSWORD)
                .withStatus(LoginVerification.Status.PENDING)
                .build());

        methods.add(LoginVerification.Method.USERNAME_PASSWORD);
    }

    private LoginRequest saveLoginRequest(String username, String level, Map<String, String> attributes) {
        LoginRequest.Builder builder = new LoginRequest.Builder()
                .withUsername(username)
                .withReference(uuid())
                .withLevel(level)
                .withStatus(LoginRequest.Status.PENDING)
                .withExpiresAt(now().plusMinutes(10));

        attributes.forEach((key, value) -> builder.withAttribute(key, value));

        return loginRequestRepository.save(builder.build());
    }

}
