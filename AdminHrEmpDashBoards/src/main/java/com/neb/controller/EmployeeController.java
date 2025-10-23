package com.neb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.neb.dto.EmployeeResponseDto;
import com.neb.dto.LoginRequestDto;
import com.neb.dto.ResponseMessage;
import com.neb.service.EmployeeService;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService service;
	
	@PostMapping("/login")
	public ResponseEntity<ResponseMessage<EmployeeResponseDto>> login(@RequestBody LoginRequestDto loginReq){
		
		EmployeeResponseDto loginRes = service.login(loginReq);
		
		return ResponseEntity.ok(new ResponseMessage<EmployeeResponseDto>(HttpStatus.OK.value(), HttpStatus.OK.name(), "employee login successfully", loginRes));
	}
	
	
}
