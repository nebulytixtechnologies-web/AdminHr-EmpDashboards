package com.neb.service;

import java.util.List;

import com.neb.dto.AddEmployeeRequestDto;
import com.neb.dto.AddEmployeeResponseDto;
import com.neb.dto.EmployeeDetailsResponseDto;
import com.neb.dto.EmployeeResponseDto;
import com.neb.dto.LoginRequestDto;
import com.neb.dto.PayslipDto;

public interface HrService {

	public AddEmployeeResponseDto addEmployee(AddEmployeeRequestDto addEmpReq);

	public EmployeeResponseDto login(LoginRequestDto loginReq);

	public List<EmployeeDetailsResponseDto> getEmployeeList();

	public EmployeeDetailsResponseDto getEmployee(Long id);
	
	public String deleteById(Long id);
	
	//added payslips part for getting list of payslips and downloading payslips
	public byte[] downloadPayslip(Long payslipId) throws Exception;
	public List<PayslipDto> listPayslipsForEmployee(Long employeeId);
	public EmployeeDetailsResponseDto addAttendence(Long id,int days);
	EmployeeDetailsResponseDto updateEmployee(Long id, AddEmployeeRequestDto updateReq);
}
