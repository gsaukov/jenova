package com.pro.jenova.common.data.audit;

import com.pro.jenova.common.util.audit.AuditContext;
import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.QueryExecutionListener;
import org.slf4j.Logger;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class DatabaseMetricsCollector implements QueryExecutionListener {

    private static final Logger logger = getLogger(DatabaseMetricsCollector.class);

    @Override
    public void beforeQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
        enrichAuditContext(execInfo);
    }

    @Override
    public void afterQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
        queryInfoList.forEach(queryInfo -> logger.debug(queryInfo.getQuery()));

        enrichAuditContext(execInfo);
    }

    private void enrichAuditContext(ExecutionInfo execInfo) {
        AuditContext context = AuditContext.get();

        context.incDbTimeMillis(execInfo.getElapsedTime());
        context.incDbQueriesCount(1L);
    }

}