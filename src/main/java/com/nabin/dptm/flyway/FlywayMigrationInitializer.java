package com.nabin.dptm.flyway;

import com.nabin.dptm.entity.tenancy.DataSourceConfig;
import com.nabin.dptm.jdbc.DataSourceConfigJDBC;
import com.nabin.dptm.tenancy.TenantDataSourceStorage;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
    private final DataSourceConfigJDBC dataSourceConfigJDBC;

    @PostConstruct
    public void migrate() {
        String scriptLocation = "db/migration";

        List<String> datasourceList = dataSourceConfigJDBC.findNonPublicDataConfigList()
                .stream()
                .map(DataSourceConfig::getName)
                .collect(Collectors.toList());

        for (String datasourceName : datasourceList) {

            Flyway flyway = Flyway.configure()
                    .locations(scriptLocation)
                    .baselineOnMigrate(Boolean.TRUE)
                    .dataSource(tenantDataSourceStorage.getDataSource(datasourceName))
                    .load();

            flyway.migrate();
        }
    }
}
