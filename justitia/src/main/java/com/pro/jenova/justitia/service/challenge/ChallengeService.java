package com.pro.jenova.justitia.service.challenge;

import com.pro.jenova.justitia.data.entity.Challenge.Type;

public interface ChallengeService {

    boolean complete(String reference, Type type);

}
