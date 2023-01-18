package com.nabin.dptm.hibernateconfig;

import com.nabin.dptm.tenancy.TenantDataSourceStorage;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 *  For determining a data source at runtime according to the tenant identifier. We need to override the selectDataSource method.
 * @author Narendra
 * @version 1.0
 * @since 2023-01-18
 */
@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl
        extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    private DataSource defaultDS;
    private ApplicationContext context;
    private Map<String, DataSource> map = new HashMap<>();
    boolean init = false;
    private static final String DEFAULT_TENANT_ID = "public";

    public DataSourceBasedMultiTenantConnectionProviderImpl(DataSource dataSource, ApplicationContext applicationContext) {
        this.defaultDS = dataSource;
        this.context = applicationContext;
    }

    @PostConstruct
    public void load() {
        map.put(DEFAULT_TENANT_ID, defaultDS);
    }

    @Override
    protected DataSource selectAnyDataSource() {
        return map.get(DEFAULT_TENANT_ID);
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        if (!init) {
            init = true;
            TenantDataSourceStorage tenantDataSource = context.getBean(TenantDataSourceStorage.class);
            map.putAll(tenantDataSource.getAll());
        }
        return map.get(tenantIdentifier) != null ? map.get(tenantIdentifier) : map.get(DEFAULT_TENANT_ID);
    }
}
