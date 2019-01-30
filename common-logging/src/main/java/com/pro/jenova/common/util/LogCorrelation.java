package com.pro.jenova.common.util;

import static com.pro.jenova.common.util.IdUtils.uuid;

public class LogCorrelation {

    public static final String LOG_CORRELATION_ID = "logCorrelationId";

    public static final int MAX_ID_SIZE = 50;

    public static String useExistingOrCreateNew(Object correlationId) {
        if (correlationId == null) {
            return uuid();
        }

        String correlationIdStr = correlationId.toString();

        if (correlationIdStr.length() > MAX_ID_SIZE) {
            return correlationIdStr.substring(0, MAX_ID_SIZE);
        }

        return correlationIdStr;
    }

}
