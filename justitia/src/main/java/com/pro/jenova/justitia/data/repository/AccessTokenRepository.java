package com.pro.jenova.justitia.data.repository;

import com.pro.jenova.justitia.data.entity.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface AccessTokenRepository extends JpaRepository<AccessToken, String> {

    Optional<AccessToken> findByJti(String jti);

}
