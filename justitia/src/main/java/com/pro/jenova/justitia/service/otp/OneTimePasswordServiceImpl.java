package com.pro.jenova.justitia.service.otp;

import com.pro.jenova.justitia.data.entity.LoginRequest;
import com.pro.jenova.justitia.data.entity.LoginVerification;
import com.pro.jenova.justitia.data.repository.LoginRequestRepository;
import com.pro.jenova.justitia.data.repository.LoginVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OneTimePasswordServiceImpl implements OneTimePasswordService {

    @Autowired
    private LoginRequestRepository loginRequestRepository;

    @Autowired
    private LoginVerificationRepository loginVerificationRepository;

    @Override
    public void register(String reference, String oneTimePassword) {
        loginRequestRepository.findByReference(reference).ifPresent(
                loginRequest -> register(loginRequest, oneTimePassword));
    }

    private void register(LoginRequest loginRequest, String oneTimePassword) {
        loginVerificationRepository.findByLoginRequestAndMethod(loginRequest,
                LoginVerification.Method.ONE_TIME_PASSWORD).ifPresent(
                loginVerification -> verify(loginVerification, oneTimePassword));
    }

    private void verify(LoginVerification loginVerification, String oneTimePassword) {
        if (oneTimePassword.equals(loginVerification.getValue())) {
            loginVerification.setStatus(LoginVerification.Status.COMPLETED);
        }
    }

}
