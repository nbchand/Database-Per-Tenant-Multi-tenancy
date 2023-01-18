package com.nabin.dptm.repo;

import com.nabin.dptm.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Narendra
 * @version 1.0
 * @since 2023-01-18
 */
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
}
