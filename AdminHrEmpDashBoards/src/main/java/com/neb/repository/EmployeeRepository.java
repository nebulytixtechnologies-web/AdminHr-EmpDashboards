package com.neb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.neb.entity.Employee;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmailAndPasswordAndLoginRole(String email, String password, String loginRole);

    boolean existsByEmail(String email);
}
