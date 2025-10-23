package com.neb.service;

import com.neb.dto.EmployeeResponseDto;
import com.neb.dto.LoginRequestDto;

public interface EmployeeService {

	public EmployeeResponseDto login(LoginRequestDto loginReq);
}
