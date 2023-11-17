package com.pd.gilgeorigoreuda.common.config.database;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
public class DataSourceConfiguration {

    public static final String MASTER_DATASOURCE = "MASTER_DATASOURCE";
    public static final String SLAVE_DATASOURCE = "SLAVE_DATASOURCE";

    @Bean
    @Qualifier(MASTER_DATASOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create()
                .build();
    }

    @Bean
    @Qualifier(SLAVE_DATASOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create()
                .build();
    }

    @Bean
    public DataSource routingDataSource(
            @Qualifier(MASTER_DATASOURCE) final DataSource masterDataSource,
            @Qualifier(SLAVE_DATASOURCE) final DataSource slaveDataSource
    ) {
        final RoutingDataSource routingDataSource = new RoutingDataSource();

        final HashMap<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(MASTER_DATASOURCE, masterDataSource);
        dataSourceMap.put(SLAVE_DATASOURCE, slaveDataSource);

        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);

        return routingDataSource;
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        final DataSource determinedDataSource = routingDataSource(masterDataSource(), slaveDataSource());
        return new LazyConnectionDataSourceProxy(determinedDataSource);
    }

}
