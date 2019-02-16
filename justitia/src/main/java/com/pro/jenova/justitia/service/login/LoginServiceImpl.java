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

import java.util.List;
import java.util.Map;

import static com.pro.jenova.common.util.IdUtils.uuid;
import static java.time.LocalDateTime.now;
import static java.util.Arrays.asList;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginRequestRepository loginRequestRepository;

    @Autowired
    private LoginVerificationRepository loginVerificationRepository;

    @Autowired
    private OneTimePasswordGenerator oneTimePasswordGenerator;

    @Autowired
    private OneTimePasswordProducer oneTimePasswordProducer;

    @Override
    public LoginServiceResult initiate(String username, Map<String, String> attributes) {
        invalidateAllExistingLoginRequests(username);
        LoginRequest loginRequest = saveLoginRequest(username, attributes);
        List<LoginVerification.Method> methods = saveLoginVerificationMethods(loginRequest);

        return new LoginServiceResult.Builder()
                .withReference(loginRequest.getReference())
                .withVerifications(methods)
                .build();
    }

    private void invalidateAllExistingLoginRequests(String username) {
        loginRequestRepository.findByUsername(username).forEach(request ->
                request.setStatus(LoginRequest.Status.INVALIDATED));
    }

    private List<LoginVerification.Method> saveLoginVerificationMethods(LoginRequest loginRequest) {
        List<LoginVerification.Method> methods = asList(LoginVerification.Method.USERNAME_PASSWORD,
                LoginVerification.Method.ONE_TIME_PASSWORD);

        loginVerificationRepository.save(new LoginVerification.Builder()
                .withLoginRequest(loginRequest)
                .withMethod(LoginVerification.Method.USERNAME_PASSWORD)
                .withStatus(LoginVerification.Status.PENDING)
                .build());

        String oneTimePassword = oneTimePasswordGenerator.generate();

        loginVerificationRepository.save(new LoginVerification.Builder()
                .withLoginRequest(loginRequest)
                .withMethod(LoginVerification.Method.ONE_TIME_PASSWORD)
                .withStatus(LoginVerification.Status.PENDING)
                .withValue(oneTimePassword)
                .build());

        oneTimePasswordProducer.send(loginRequest.getUsername(), oneTimePassword);

        return methods;
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
