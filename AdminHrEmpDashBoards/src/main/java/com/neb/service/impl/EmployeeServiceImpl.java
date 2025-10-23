package com.neb.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neb.dto.EmployeeResponseDto;
import com.neb.dto.LoginRequestDto;
import com.neb.entity.Employee;
import com.neb.repository.EmployeeRepository;
import com.neb.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository empRepo;

    @Autowired
    private ModelMapper mapper;

    // --------- LOGIN ----------
    @Override
    public EmployeeResponseDto login(LoginRequestDto loginReq) {

        // fetch employee from DB
        Employee emp = empRepo.findByEmailAndPasswordAndLoginRole(
                loginReq.getEmail(),
                loginReq.getPassword(),
                loginReq.getLoginRole()
        ).orElseThrow(() -> new RuntimeException("Invalid credentials"));

        // map entity to DTO
        EmployeeResponseDto loginRes = mapper.map(emp, EmployeeResponseDto.class);

        return loginRes;
    }
}
