package com.pro.jenova.common.data.audit;

import com.pro.jenova.common.util.audit.AuditContext;
import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.logging.AbstractQueryLoggingListener;

import java.util.List;

public class DataSourceAuditListener extends AbstractQueryLoggingListener {

    @Override
    public void afterQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
        AuditContext auditContext = AuditContext.DIAGNOSTICS.get();
        auditContext.incDbQueriesCount(queryInfoList.size());
        auditContext.incDbTimeMillis(execInfo.getElapsedTime());
    }

    @Override
    protected void writeLog(String message) {
        // NOP
    }

}