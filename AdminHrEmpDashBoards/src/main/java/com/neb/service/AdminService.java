package com.neb.service;

import java.util.List;

import com.neb.dto.AddEmployeeRequestDto;
import com.neb.dto.AddEmployeeResponseDto;
import com.neb.dto.AddWorkRequestDto;
import com.neb.dto.EmployeeDetailsResponseDto;
import com.neb.dto.EmployeeResponseDto;
import com.neb.dto.LoginRequestDto;
import com.neb.dto.WorkResponseDto;

public interface AdminService {

	public EmployeeResponseDto login(LoginRequestDto loginReq);
	public AddEmployeeResponseDto addEmployee(AddEmployeeRequestDto addEmpReq);
	public List<EmployeeDetailsResponseDto> getEmployeeList();
	public WorkResponseDto assignWork(AddWorkRequestDto request);
	public List<WorkResponseDto> getAllWorks();
	public List<WorkResponseDto> getWorkByEmployee(Long empId);
	
}
