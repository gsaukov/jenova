package com.pro.jenova.omnidrive.data.repository;

import com.pro.jenova.omnidrive.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    @Transactional
    long removeByUsername(String username);

}
