package com.pro.jenova.omnidrive.util;

public class AuditContext {

    private static final ThreadLocal<String> CONTEXT =
            ThreadLocal.withInitial(() -> "system");

    public static String username() {
        return CONTEXT.get();
    }

    public static void username(String username) {
        CONTEXT.set(username);
    }

}
