package com.neb.service;

import java.util.List;

import com.neb.dto.AddEmployeeRequestDto;
import com.neb.dto.AddEmployeeResponseDto;
import com.neb.dto.EmployeeDetailsResponseDto;
import com.neb.dto.EmployeeResponseDto;
import com.neb.dto.LoginRequestDto;

public interface HrService {

	public AddEmployeeResponseDto addEmployee(AddEmployeeRequestDto addEmpReq);

	public EmployeeResponseDto login(LoginRequestDto loginReq);

	public List<EmployeeDetailsResponseDto> getEmployeeList();

	public EmployeeDetailsResponseDto getEmployee(Long id);
	
	public String deleteById(Long id);
}
