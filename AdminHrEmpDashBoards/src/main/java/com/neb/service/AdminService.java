package com.neb.service;

import java.util.List;

import com.neb.dto.AddEmployeeRequestDto;
import com.neb.dto.AddEmployeeResponseDto;
import com.neb.dto.EmployeeDetailsResponseDto;
import com.neb.dto.EmployeeResponseDto;
import com.neb.dto.LoginRequestDto;

public interface AdminService {

	public EmployeeResponseDto login(LoginRequestDto loginReq);
	public AddEmployeeResponseDto addEmployee(AddEmployeeRequestDto addEmpReq);
	public List<EmployeeDetailsResponseDto> getEmployeeList();
	
}
