package com.pro.jenova.justitia.service.login;

import com.pro.jenova.justitia.data.entity.Challenge;
import com.pro.jenova.justitia.data.entity.Login;

import java.util.List;

public class LoginResult {

    private Login login;
    private List<Challenge> challenges;

    public LoginResult(Login login, List<Challenge> challenges) {
        this.login = login;
        this.challenges = challenges;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public List<Challenge> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
    }

}
