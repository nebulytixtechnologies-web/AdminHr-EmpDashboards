package com.neb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neb.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmailAndPasswordAndLoginRole(String email, String password, String loginRole);

    boolean existsByEmail(String email);
    
    // 1️ Get all employees excluding admin
    List<Employee> findByLoginRoleNot(String loginRole);

    // 2️ Get all employees excluding admin and hr
    List<Employee> findByLoginRoleNotIn(List<String> roles);
    Optional<Employee> findByEmail(String email);
}
