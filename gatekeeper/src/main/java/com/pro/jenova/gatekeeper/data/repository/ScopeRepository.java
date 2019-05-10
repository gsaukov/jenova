package com.pro.jenova.gatekeeper.data.repository;


import com.pro.jenova.gatekeeper.data.entity.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ScopeRepository extends JpaRepository<Scope, String> {

    List<Scope> findByClientId(String clientId);

}
