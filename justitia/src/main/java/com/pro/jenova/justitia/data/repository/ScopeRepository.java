package com.pro.jenova.justitia.data.repository;

import com.pro.jenova.justitia.data.entity.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface ScopeRepository extends JpaRepository<Scope, String> {

    Optional<Scope> findByClientIdAndName(String clientId, String name);

    boolean existsByClientIdAndName(String clientId, String name);

    long removeByClientIdAndName(String clientId, String name);

    List<Scope> findByClientId(String clientId);

}
