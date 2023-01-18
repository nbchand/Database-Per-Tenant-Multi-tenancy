package com.nabin.dptm.repo.tenancy;

import com.nabin.dptm.entity.tenancy.DataSourceConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Narendra
 * @version 1.0
 * @since 2023-01-18
 */
public interface DataSourceConfigRepo extends JpaRepository<DataSourceConfig, Integer> {
    Optional<DataSourceConfig> findByName(String name);
}
