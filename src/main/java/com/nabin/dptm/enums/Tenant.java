package com.nabin.dptm.enums;

/**
 * @author Narendra
 * @version 1.0
 * @since 2023-01-23
 */
public enum Tenant {
    //    PUBLIC(0, "public"),
    TEST1(2, "test1"),
    TEST2(3, "test2");

    private Integer key;
    private String value;

    Tenant(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    public Tenant getByKey(Integer key) {
        for (Tenant tenant : Tenant.values()) {
            if (tenant.key.equals(key)) {
                return tenant;
            }
        }
        throw new RuntimeException("Tenant enum with given key " + key + " not found");
    }
}
