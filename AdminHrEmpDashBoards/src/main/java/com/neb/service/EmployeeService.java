package com.neb.service;

import java.util.List;

import com.neb.dto.EmployeeResponseDto;
import com.neb.dto.LoginRequestDto;
import com.neb.dto.PayslipDto;
import com.neb.entity.Payslip;

public interface EmployeeService {

	public EmployeeResponseDto login(LoginRequestDto loginReq);
	public Payslip generatePayslip(Long employeeId, String monthYear)throws Exception;
	public byte[] downloadPayslip(Long payslipId) throws Exception;
	public List<PayslipDto> listPayslipsForEmployee(Long employeeId);
}
