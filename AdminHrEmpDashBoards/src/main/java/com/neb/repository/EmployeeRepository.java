package com.neb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neb.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
