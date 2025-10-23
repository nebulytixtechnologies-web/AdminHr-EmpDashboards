package com.neb.service;

import com.neb.dto.AddEmployeeRequestDto;
import com.neb.dto.AddEmployeeResponseDto;

public interface HrService {

	public AddEmployeeResponseDto addEmployee(AddEmployeeRequestDto addEmpReq);
}
