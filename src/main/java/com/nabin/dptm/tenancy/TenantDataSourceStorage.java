package com.nabin.dptm.tenancy;

import com.nabin.dptm.entity.tenancy.DataSourceConfig;
import com.nabin.dptm.repo.tenancy.DataSourceConfigRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to store the database connection details for each tenant
 * @author Narendra
 * @version 1.0
 * @since 2023-01-18
 */
@Component
public class TenantDataSourceStorage {
    private HashMap<String, DataSource> dataSources = new HashMap<>();

    @Lazy
    @Autowired
    private DataSourceConfigRepo configRepo;

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
     * @return
     */
    @PostConstruct
    public Map<String, DataSource> getAll() {
        List<DataSourceConfig> configList = configRepo.findAll();
        Map<String, DataSource> result = new HashMap<>();

        for (DataSourceConfig config : configList) {
            DataSource dataSource = getDataSource(config.getName());
            result.put(config.getName(), dataSource);
        }

        return result;
    }

    private DataSource createDataSource(String name) {

        DataSourceConfig config = configRepo.findByName(name)
                .orElse(null);

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
