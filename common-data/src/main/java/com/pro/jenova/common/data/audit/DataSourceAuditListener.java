package com.pro.jenova.common.data.audit;

import com.pro.jenova.common.util.audit.AuditContext;
import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.logging.AbstractQueryLoggingListener;
import org.slf4j.Logger;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class DataSourceAuditListener extends AbstractQueryLoggingListener {

    private static final Logger logger = getLogger(DataSourceAuditListener.class);

    @Override
    public void afterQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
        super.afterQuery(execInfo, queryInfoList);

        enrichAuditContext(execInfo);
    }

    private void enrichAuditContext(ExecutionInfo execInfo) {
        AuditContext.getIfExists().ifPresent(context -> {
            context.incDbTimeMillis(execInfo.getElapsedTime());
            context.incDbQueriesCount(1L);
        });
    }

    @Override
    protected void writeLog(String message) {
        logger.debug(message);
    }

}