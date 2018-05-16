package com.pro.jenova.omnidrive.util;

import static java.lang.ThreadLocal.withInitial;

public class AuditContext {

    private static final ThreadLocal<String> CONTEXT =
            withInitial(() -> "system");

    public static String username() {
        return CONTEXT.get();
    }

    public static void username(String username) {
        CONTEXT.set(username);
    }

}
