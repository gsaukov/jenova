package com.pro.jenova.justitia.service.oob;

import com.pro.jenova.justitia.data.entity.LoginVerification;
import com.pro.jenova.justitia.data.repository.LoginRequestRepository;
import com.pro.jenova.justitia.data.repository.LoginVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OutOfBandRegistererImpl implements OutOfBandRegisterer {

    @Autowired
    private LoginRequestRepository loginRequestRepository;

    @Autowired
    private LoginVerificationRepository loginVerificationRepository;

    @Override
    public void register(String reference) {
        loginRequestRepository.findByReference(reference).ifPresent(loginRequest ->
                loginVerificationRepository.findByLoginRequestAndMethod(
                        loginRequest, LoginVerification.Method.OUT_OF_BAND).ifPresent(loginVerification ->
                        loginVerification.setValue("DONE"))
        );
    }

}
