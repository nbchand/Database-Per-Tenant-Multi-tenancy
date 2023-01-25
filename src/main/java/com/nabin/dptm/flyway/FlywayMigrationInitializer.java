package com.nabin.dptm.flyway;

import com.nabin.dptm.enums.Tenant;
import com.nabin.dptm.tenancy.TenantDataSourceStorage;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Narendra
 * @version 1.0
 * @since 2023-01-23
 */
@Component
@RequiredArgsConstructor
public class FlywayMigrationInitializer {

    private final TenantDataSourceStorage tenantDataSourceStorage;

    @PostConstruct
    public void migrate() {
        String scriptLocation = "db/migration";

        for (Tenant tenant : Tenant.values()) {
            String dbName = tenant.getValue();

            Flyway flyway = Flyway.configure()
                    .locations(scriptLocation)
                    .baselineOnMigrate(Boolean.TRUE)
                    .dataSource(tenantDataSourceStorage.getDataSource(dbName))
                    .load();

            flyway.migrate();
        }
    }
}
