package com.pro.jenova.omnidrive.data.repository;

import com.pro.jenova.omnidrive.data.entity.Authority;
import com.pro.jenova.omnidrive.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface AuthorityRepository extends JpaRepository<Authority, String> {

    List<Authority> findByUser(User user);

    boolean existsByUserAndRole(User user, String role);

    @Transactional
    long removeByUserAndRole(User user, String role);

}
