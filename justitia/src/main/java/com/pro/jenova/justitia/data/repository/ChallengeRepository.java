package com.pro.jenova.justitia.data.repository;

import com.pro.jenova.justitia.data.entity.Challenge;
import com.pro.jenova.justitia.data.entity.Challenge.Type;
import com.pro.jenova.justitia.data.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface ChallengeRepository extends JpaRepository<Challenge, String> {

    List<Challenge> findByLogin(Login login);

    Optional<Challenge> findByLoginAndType(Login login, Type type);

}
