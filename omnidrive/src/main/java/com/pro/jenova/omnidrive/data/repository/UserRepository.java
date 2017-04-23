package com.pro.jenova.omnidrive.data.repository;

import com.pro.jenova.omnidrive.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);

}
