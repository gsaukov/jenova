package com.pro.jenova.util.generator;

import static java.lang.Character.MIN_VALUE;
import static java.util.UUID.randomUUID;

public class UniqueId {

    public static final String uuid() {
        return randomUUID().toString().replace('-', MIN_VALUE).toUpperCase();
    }

}
