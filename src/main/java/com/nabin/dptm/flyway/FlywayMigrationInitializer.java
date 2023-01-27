package com.nabin.dptm.flyway;

import com.nabin.dptm.entity.tenancy.DataSourceConfig;
import com.nabin.dptm.jdbc.DataSourceConfigFetcher;
import com.nabin.dptm.tenancy.TenantDataSourceStorage;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Narendra
 * @version 1.0
 * @since 2023-01-23
 */
@Component
@RequiredArgsConstructor
public class FlywayMigrationInitializer {

    private final TenantDataSourceStorage tenantDataSourceStorage;
    private final DataSourceConfigFetcher dataSourceConfigFetcher;

    @PostConstruct
    public void migrate() {

        //create data_source_config table at our default database
        //data_source_config table is used to store the information of other tenant database addresses
        String publicMigrationLocation = "db/default-database";
        performMigration(tenantDataSourceStorage.getPublicDatasource(), publicMigrationLocation);

        List<String> datasourceList = dataSourceConfigFetcher.findNonPublicDataConfigList()
                .stream()
                .map(DataSourceConfig::getName)
                .collect(Collectors.toList());

        String scriptLocation = "db/tenant-migrations";

        //perform ddl operation of required entities for tenant-databases
        datasourceList.forEach(
                datasourceName ->
                        performMigration(
                                tenantDataSourceStorage.getDataSource(datasourceName),
                                scriptLocation
                        )
        );

    }

    private void performMigration(DataSource dataSource, String scriptLocation) {

        Flyway flyway = Flyway.configure()
                .locations(scriptLocation)
                .baselineOnMigrate(Boolean.TRUE)
                .dataSource(dataSource)
                .load();

        flyway.migrate();
    }

}
