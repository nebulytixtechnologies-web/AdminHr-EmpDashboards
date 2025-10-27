package com.neb.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neb.entity.Employee;
import com.neb.entity.Work;

public interface WorkRepository extends JpaRepository<Work, Long> {
	 List<Work> findByEmployeeId(Long employeeId);
	 List<Work> findByEmployee(Employee emp);

}
