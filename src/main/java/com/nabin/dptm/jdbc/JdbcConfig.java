package com.nabin.dptm.jdbc;

import com.nabin.dptm.entity.tenancy.DataSourceConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Narendra
 * @version 1.0
 * @since 2023-01-19
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JdbcConfig {

    private final DataSourceProperties dataSourceProperties;
    private Connection connection;
    private Statement statement;

    private void createConnection() throws SQLException {
        connection = DriverManager.getConnection(dataSourceProperties.getUrl(),
                dataSourceProperties.getUsername(),
                dataSourceProperties.getPassword());
        statement = connection.createStatement();
    }

    private void closeConnection() {
        try {
            connection.close();
        } catch (SQLException exception) {
            log.error("Database connection closing failed: {}", exception.getMessage());
        }
    }

    public DataSourceConfig findDataSourceConfig(String query) {
        DataSourceConfig dataSourceConfig = null;
        try {
            createConnection();
            ResultSet resultSet = statement.executeQuery(query);
            List<DataSourceConfig> dataSourceConfigList = resultSetToDataSourceConfig(resultSet);

            if (!dataSourceConfigList.isEmpty()) {
                dataSourceConfig = dataSourceConfigList.get(0);
            }
        } catch (SQLException exception) {
            log.error("DataSourceConfig can't be fetched from database: {}", exception.getMessage());
        } finally {
            closeConnection();
        }
        return dataSourceConfig;
    }

    public List<DataSourceConfig> findDataSourceConfigList(String query) {

        List<DataSourceConfig> dataSourceConfigList;

        try {
            createConnection();
            ResultSet resultSet = statement.executeQuery(query);
            dataSourceConfigList = resultSetToDataSourceConfig(resultSet);
        } catch (SQLException exception) {
            log.error("DataSourceConfig list can't be fetched from database: {}", exception.getMessage());
            dataSourceConfigList = Collections.emptyList();
        } finally {
            closeConnection();
        }

        return dataSourceConfigList;
    }

    private List<DataSourceConfig> resultSetToDataSourceConfig(ResultSet resultSet) throws SQLException {

        List<DataSourceConfig> dataSourceConfigList = new ArrayList<>();

        while (resultSet.next()) {
            DataSourceConfig dataSourceConfig = new DataSourceConfig();

            dataSourceConfig.setId(resultSet.getInt("id"));
            dataSourceConfig.setDriverClassName(resultSet.getString("driver_class_name"));
            dataSourceConfig.setUrl(resultSet.getString("url"));
            dataSourceConfig.setName(resultSet.getString("name"));
            dataSourceConfig.setUsername(resultSet.getString("username"));
            dataSourceConfig.setPassword(resultSet.getString("password"));

            dataSourceConfigList.add(dataSourceConfig);
        }

        return dataSourceConfigList;
    }

}
