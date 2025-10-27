package com.neb.service;
//original

import java.time.LocalDate;
import java.util.List;

import com.neb.dto.EmployeeResponseDto;
import com.neb.dto.LoginRequestDto;
import com.neb.entity.Employee;
import com.neb.entity.Payslip;
import com.neb.entity.Work;

public interface EmployeeService {

	public EmployeeResponseDto login(LoginRequestDto loginReq);
	public Payslip generatePayslip(Long employeeId, String monthYear)throws Exception;
	public Employee getEmployeeById(Long id);
	public List<Work> getTasksByEmployee(Long employeeId);
    public Work submitReport(Long taskId, String reportDetails, LocalDate submittedDate);
    public Employee getEmployeeByEmail(String email);
}
