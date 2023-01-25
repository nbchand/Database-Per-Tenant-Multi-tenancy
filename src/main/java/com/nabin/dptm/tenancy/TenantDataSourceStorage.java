package com.nabin.dptm.tenancy;

import com.nabin.dptm.entity.tenancy.DataSourceConfig;
import com.nabin.dptm.jdbc.DataSourceConfigJDBC;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to store the database connection details for each tenant
 *
 * @author Narendra
 * @version 1.0
 * @since 2023-01-18
 */
@Component
@RequiredArgsConstructor
public class TenantDataSourceStorage {
    private HashMap<String, DataSource> dataSources = new HashMap<>();

    private final DataSourceConfigJDBC dataSourceConfigJDBC;

    public DataSource getDataSource(String name) {
        if (dataSources.get(name) != null) {
            return dataSources.get(name);
        }
        DataSource dataSource = createDataSource(name);
        if (dataSource != null) {
            dataSources.put(name, dataSource);
        }
        return dataSource;
    }

    /**
     * We will load the connection details during server startup using @PostConstruct.
     *
     * @returng
     */
    @PostConstruct
    public Map<String, DataSource> getAll() {
        List<DataSourceConfig> configList = dataSourceConfigJDBC.findAllDataSourceConfig();
        Map<String, DataSource> result = new HashMap<>();

        for (DataSourceConfig config : configList) {
            DataSource dataSource = getDataSource(config.getName());
            result.put(config.getName(), dataSource);
        }

        return result;
    }

    private DataSource createDataSource(String name) {

        DataSourceConfig config = dataSourceConfigJDBC.findDataSourceConfigByName(name);

        if (config == null) {
            return null;
        }

        return DataSourceBuilder
                .create()
                .driverClassName(config.getDriverClassName())
                .username(config.getUsername())
                .password(config.getPassword())
                .url(config.getUrl())
                .build();
    }
}
