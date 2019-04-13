package com.pro.jenova.justitia.service.login;

import com.pro.jenova.justitia.data.entity.Challenge;
import com.pro.jenova.justitia.data.entity.Login;
import com.pro.jenova.justitia.data.repository.ChallengeRepository;
import com.pro.jenova.justitia.data.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.pro.jenova.common.util.IdUtils.uuid;
import static com.pro.jenova.justitia.data.entity.Challenge.Status.NOT_APPLICABLE;
import static com.pro.jenova.justitia.data.entity.Challenge.Status.PENDING;
import static com.pro.jenova.justitia.data.entity.Challenge.Type.*;
import static java.time.LocalDateTime.now;
import static java.util.Arrays.stream;
import static org.apache.commons.lang.RandomStringUtils.random;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    private static final String PISP = "pisp";

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Override
    public LoginResult init(String clientId, String username, String scopes, Map<String, String> params) {
        loginRepository.removeByClientIdAndUsernameAndScopes(clientId, username, scopes);

        String reference = uuid();

        Login login = loginRepository.save(new Login.Builder()
                .withReference(reference)
                .withClientId(clientId)
                .withUsername(username)
                .withScopes(scopes)
                .withParams(params)
                .withExpiresAt(now().plusMinutes(10))
                .withCompleted(false)
                .build());

        List<Challenge> challenges = getChallenges(login);
        challenges.forEach(challenge -> challengeRepository.save(challenge));

        return new LoginResult(login, challenges);
    }

    private List<Challenge> getChallenges(Login login) {
        boolean requiresElevated = stream(login.getScopes().split(","))
                .map(String::toLowerCase).anyMatch(PISP::equals);

        List<Challenge> challenges = new ArrayList<>();

        if (requiresElevated) {
            addOutOfBand(login, challenges);
            return challenges;
        }

        addCredentials(login, challenges);
        addOneTimePassword(login, challenges);
        return challenges;
    }

    private void addOneTimePassword(Login login, List<Challenge> challenges) {
        challenges.add(new Challenge.Builder()
                .withLogin(login)
                .withType(ONE_TIME_PASSWORD)
                .withStatus(NOT_APPLICABLE)
                .withOneTimePassword(randomOneTimePassword())
                .build());
    }

    private void addCredentials(Login login, List<Challenge> challenges) {
        challenges.add(new Challenge.Builder()
                .withLogin(login)
                .withType(CREDENTIALS)
                .withStatus(NOT_APPLICABLE)
                .build());
    }

    private void addOutOfBand(Login login, List<Challenge> challenges) {
        challenges.add(new Challenge.Builder()
                .withLogin(login)
                .withType(OUT_OF_BAND)
                .withStatus(PENDING)
                .build());
    }

    private String randomOneTimePassword() {
        return random(6, true, true).toUpperCase();
    }

}
