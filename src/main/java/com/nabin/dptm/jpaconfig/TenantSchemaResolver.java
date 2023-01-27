package com.nabin.dptm.jpaconfig;

import com.nabin.dptm.tenancy.TenantContext;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

/**
 * It tells hibernate which is the current configured tenant. It uses the previous ThreadLocal variable set by the interceptor.
 *
 * @author Narendra
 * @version 1.0
 * @since 2023-01-18
 */
@Component
public class TenantSchemaResolver implements CurrentTenantIdentifierResolver {

    private String defaultTenant = "public";

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenant = TenantContext.getCurrentTenant();

        if (tenant == null) {
            return defaultTenant;
        }

        return tenant;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
