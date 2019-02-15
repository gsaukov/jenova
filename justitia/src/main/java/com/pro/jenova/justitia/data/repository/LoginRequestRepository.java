package com.pro.jenova.justitia.data.repository;

import com.pro.jenova.justitia.data.entity.LoginRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface LoginRequestRepository extends JpaRepository<LoginRequest, String> {

    Optional<LoginRequest> findByReference(String reference);

    List<LoginRequest> findByUsername(String username);

}
