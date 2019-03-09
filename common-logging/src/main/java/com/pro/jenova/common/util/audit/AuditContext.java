package com.pro.jenova.common.util.audit;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;


public class AuditContext {

    private static final ThreadLocal<AuditContext> DIAGNOSTICS = new ThreadLocal<>();

    private Long dbQueriesCount;
    private Long dbTimeMillis;

    public static AuditContext getReady() {
        AuditContext context = DIAGNOSTICS.get();

        if (context == null) {
            context = new AuditContext();
            DIAGNOSTICS.set(context);
        }

        context.reset();
        return context;
    }

    public static Optional<AuditContext> getIfExists() {
        AuditContext context = DIAGNOSTICS.get();

        if (context != null) {
            return of(context);
        }

        DIAGNOSTICS.remove();
        return empty();
    }

    public static void cleanup() {
        DIAGNOSTICS.remove();
    }

    public Long getDbQueriesCount() {
        return dbQueriesCount;
    }

    public Long getDbTimeMillis() {
        return dbTimeMillis;
    }

    public void incDbQueriesCount(long count) {
        dbQueriesCount = increase(dbQueriesCount, count);
    }

    public void incDbTimeMillis(long millis) {
        dbTimeMillis = increase(dbTimeMillis, millis);
    }

    private Long increase(Long value, long increment) {
        return value == null ? increment : value + increment;
    }

    private void reset() {
        dbQueriesCount = null;
        dbTimeMillis = null;
    }

}
