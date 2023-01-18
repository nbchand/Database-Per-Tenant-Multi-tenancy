package com.nabin.dptm.entity.tenancy;

import lombok.*;

import javax.persistence.*;

/**
 * @author Narendra
 * @version 1.0
 * @since 2023-01-18
 */
@Entity
@Table(name = "data_source_config")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataSourceConfig {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "driver_class_name", nullable = false)
    private String driverClassName;

    @Column(nullable = false)
    private boolean initialize;

}
