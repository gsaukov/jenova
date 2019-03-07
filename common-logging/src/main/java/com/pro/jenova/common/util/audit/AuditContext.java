package com.pro.jenova.common.util.audit;

public class AuditContext {

    public static final ThreadLocal<AuditContext> DIAGNOSTICS = ThreadLocal.withInitial(AuditContext::new);

    private Long dbQueriesCount;
    private Long dbTimeMillis;

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

}
