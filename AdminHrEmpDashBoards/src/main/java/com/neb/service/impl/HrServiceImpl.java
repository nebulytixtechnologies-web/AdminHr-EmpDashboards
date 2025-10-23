package com.neb.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neb.dto.AddEmployeeRequestDto;
import com.neb.dto.AddEmployeeResponseDto;
import com.neb.entity.Employee;
import com.neb.repository.EmployeeRepository;
import com.neb.service.HrService;

@Service
public class HrServiceImpl implements HrService{

	@Autowired
    private EmployeeRepository empRepo;

    @Autowired
    private ModelMapper mapper;
    
	// --------- ADD EMPLOYEE ----------
    @Override
    public AddEmployeeResponseDto addEmployee(AddEmployeeRequestDto addEmpReq) {

        // check if employee with same email already exists
        if (empRepo.existsByEmail(addEmpReq.getEmail())) {
            throw new RuntimeException("Employee with email " + addEmpReq.getEmail() + " already exists");
        }

        // map DTO to entity
        Employee emp = mapper.map(addEmpReq, Employee.class);

        // save entity
        Employee savedEmp = empRepo.save(emp);

        // map saved entity to response DTO
        AddEmployeeResponseDto addEmpRes = mapper.map(savedEmp, AddEmployeeResponseDto.class);

        return addEmpRes;
    }
}
