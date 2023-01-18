package com.nabin.dptm.controllers;

import com.nabin.dptm.entity.Employee;
import com.nabin.dptm.repo.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Narendra
 * @version 1.0
 * @since 2023-01-18
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    private final EmployeeRepo employeeRepo;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeRepo.save(employee), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(employeeRepo.findById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(employeeRepo.findAll(), HttpStatus.OK);
    }
}
