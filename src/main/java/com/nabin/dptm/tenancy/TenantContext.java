package com.nabin.dptm.tenancy;

/**
 * @author Narendra
 * @version 1.0
 * @since 2023-01-18
 */
public class TenantContext {

    /**
     * The ThreadLocal class in Java allows programmers to create variables that are accessible only to the thread that created them.
     */
    private static ThreadLocal<String> currentTenant = new InheritableThreadLocal<>();

    public static String getCurrentTenant() {
        return currentTenant.get();
    }

    public static void setCurrentTenant(String tenant) {
        currentTenant.set(tenant);
    }

    public static void clear() {
        currentTenant.set(null);
    }
}
