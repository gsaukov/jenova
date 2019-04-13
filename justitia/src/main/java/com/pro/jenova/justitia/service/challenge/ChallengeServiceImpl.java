package com.pro.jenova.justitia.service.challenge;

import com.pro.jenova.justitia.data.entity.Challenge;
import com.pro.jenova.justitia.data.entity.Challenge.Type;
import com.pro.jenova.justitia.data.entity.Login;
import com.pro.jenova.justitia.data.repository.ChallengeRepository;
import com.pro.jenova.justitia.data.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.pro.jenova.justitia.data.entity.Challenge.Status.COMPLETED;
import static com.pro.jenova.justitia.data.entity.Challenge.Status.PENDING;

@Service
@Transactional
public class ChallengeServiceImpl implements ChallengeService {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Override
    public boolean complete(String reference, Type type) {
        Optional<Login> maybeLogin = loginRepository.findByReference(reference);

        if (maybeLogin.isPresent()) {
            return complete(maybeLogin.get(), type);
        }
        return false;
    }

    private boolean complete(Login login, Type type) {
        Optional<Challenge> maybeChallenge = challengeRepository.findByLoginAndType(login, type);

        if (maybeChallenge.isPresent()) {
            return complete(maybeChallenge.get());
        }
        return false;
    }

    private boolean complete(Challenge challenge) {
        if (PENDING.equals(challenge.getStatus())) {
            challenge.setStatus(COMPLETED);
            return true;
        }
        return false;
    }

}
