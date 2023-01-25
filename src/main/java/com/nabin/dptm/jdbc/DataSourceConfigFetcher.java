package com.nabin.dptm.jdbc;

import com.nabin.dptm.entity.tenancy.DataSourceConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Narendra
 * @version 1.0
 * @since 2023-01-25
 */
@Component
@RequiredArgsConstructor
public class DataSourceConfigFetcher {
    private final JdbcConfig jdbcConfig;

    public List<DataSourceConfig> findAllDataSourceConfig() {
        String query = "select * from data_source_config";
        return jdbcConfig.findDataSourceConfigList(query);
    }

    public List<DataSourceConfig> findNonPublicDataConfigList() {
        String query = "select * from data_source_config where name not ilike \'public\'";
        return jdbcConfig.findDataSourceConfigList(query);
    }

    public DataSourceConfig findDataSourceConfigByName(String name) {
        String query = "select * from data_source_config where name = \'" + name + "\' limit 1";
        return jdbcConfig.findDataSourceConfig(query);
    }
}
