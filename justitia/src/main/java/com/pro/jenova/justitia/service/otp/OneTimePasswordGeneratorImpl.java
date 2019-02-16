package com.pro.jenova.justitia.service.otp;

import org.springframework.stereotype.Service;

import static org.apache.commons.lang.RandomStringUtils.random;

@Service
public class OneTimePasswordGeneratorImpl implements OneTimePasswordGenerator {

    @Override
    public String generate() {
        return random(6, true, true).toUpperCase();
    }

}
