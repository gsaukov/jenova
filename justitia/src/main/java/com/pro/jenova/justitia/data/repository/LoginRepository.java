package com.pro.jenova.justitia.data.repository;

import com.pro.jenova.justitia.data.entity.Login;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface LoginRepository extends JpaRepository<Login, String> {

    Optional<Login> findByReference(String reference);

    List<Login> findByClientIdAndUsernameAndScopes(String clientId, String username, String scopes);

    @EntityGraph(attributePaths = "params")
    Optional<Login> findWithParamsByReference(String reference);

}
