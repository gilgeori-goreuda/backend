package com.pd.gilgeorigoreuda.common.config.database;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static com.pd.gilgeorigoreuda.common.config.database.DataSourceType.MASTER_DATASOURCE;
import static com.pd.gilgeorigoreuda.common.config.database.DataSourceType.SLAVE_DATASOURCE;

public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        final boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();

        if (isReadOnly) {
            return SLAVE_DATASOURCE;
        }

        return MASTER_DATASOURCE;
    }

}

