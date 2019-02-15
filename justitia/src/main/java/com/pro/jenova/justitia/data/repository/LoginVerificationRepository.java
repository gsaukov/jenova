package com.pro.jenova.justitia.data.repository;

import com.pro.jenova.justitia.data.entity.LoginRequest;
import com.pro.jenova.justitia.data.entity.LoginVerification;
import com.pro.jenova.justitia.data.entity.LoginVerification.Method;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface LoginVerificationRepository extends JpaRepository<LoginVerification, String> {

    Optional<LoginVerification> findByLoginRequestAndMethod(LoginRequest loginRequest, Method method);

    List<LoginVerification> findByLoginRequest(LoginRequest loginRequest);

}
