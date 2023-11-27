package com.pd.gilgeorigoreuda.common.config.database;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.Map;

import static com.pd.gilgeorigoreuda.common.config.database.DataSourceType.Key.REPLICA_NAME;
import static com.pd.gilgeorigoreuda.common.config.database.DataSourceType.Key.SOURCE_NAME;
import static com.pd.gilgeorigoreuda.common.config.database.DataSourceType.Key.ROUTING_NAME;

@Profile({"blue", "green"})
@Configuration
public class DataSourceConfiguration {

    @Bean
    @Qualifier(SOURCE_NAME)
    @ConfigurationProperties(prefix = "spring.datasource.source")
    public DataSource sourceDataSource() {
        return DataSourceBuilder.create().build();
    }
    @Bean
    @Qualifier(REPLICA_NAME)
    @ConfigurationProperties(prefix = "spring.datasource.replica")
    public DataSource replicaDataSource() {
        return DataSourceBuilder.create().build();
    }
    @Bean
    @Qualifier(ROUTING_NAME)
    public DataSource routingDataSource(
            @Qualifier(SOURCE_NAME) DataSource sourceDataSource,
            @Qualifier(REPLICA_NAME) DataSource replicaDataSource
    ) {
        return RoutingDataSource.from(Map.of(
                DataSourceType.SOURCE, sourceDataSource,
                DataSourceType.REPLICA, replicaDataSource
        ));
    }
    @Bean
    @Primary
    public DataSource dataSource(
            @Qualifier(ROUTING_NAME) DataSource routingDataSource
    ) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

}
