package com.neb.service;


import com.neb.dto.EmployeeResponseDto;
import com.neb.dto.LoginRequestDto;
import com.neb.entity.Payslip;

public interface EmployeeService {

	public EmployeeResponseDto login(LoginRequestDto loginReq);
	public Payslip generatePayslip(Long employeeId, String monthYear)throws Exception;
}
