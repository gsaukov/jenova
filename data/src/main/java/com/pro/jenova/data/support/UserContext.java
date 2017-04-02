package com.pro.jenova.data.support;

import java.util.Optional;

import static java.util.Optional.ofNullable;

public class UserContext {

    private static final ThreadLocal<String> USERNAME = new ThreadLocal<>();

    public static Optional<String> getUsername() {
        return ofNullable(USERNAME.get());
    }

    public static void setUsername(String username) {
        USERNAME.set(username);
    }

}
