package com.neb.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neb.dto.AddEmployeeRequestDto;
import com.neb.dto.AddEmployeeResponseDto;
import com.neb.dto.EmployeeDetailsResponseDto;
import com.neb.dto.EmployeeResponseDto;
import com.neb.dto.LoginRequestDto;
import com.neb.dto.ResponseMessage;
import com.neb.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private AdminService service;
	
	@PostMapping("/login")
	public ResponseEntity<ResponseMessage<EmployeeResponseDto>> login(@RequestBody LoginRequestDto loginReq){
		
		EmployeeResponseDto loginRes = service.login(loginReq);
		
		return ResponseEntity.ok(new ResponseMessage<EmployeeResponseDto>(HttpStatus.OK.value(), HttpStatus.OK.name(), "admin login successfully", loginRes));
	}
	
	//for adding Hr
	@PostMapping("/add")
	public ResponseEntity<ResponseMessage<AddEmployeeResponseDto>> addEmployee(@RequestBody AddEmployeeRequestDto addEmpReq){
		
		AddEmployeeResponseDto addEmpRes = service.addEmployee(addEmpReq);
		
		return ResponseEntity.ok(new ResponseMessage<AddEmployeeResponseDto>(HttpStatus.OK.value(), HttpStatus.OK.name(), "Hr added successfully", addEmpRes));
	}
	
	//get employee list
	@GetMapping("/getEmpList")
	public ResponseEntity<ResponseMessage<List<EmployeeDetailsResponseDto>>> getEmployeeList(){
		
		List<EmployeeDetailsResponseDto> employeeList = service.getEmployeeList();
		
		return ResponseEntity.ok(new ResponseMessage<List<EmployeeDetailsResponseDto>>(HttpStatus.OK.value(), HttpStatus.OK.name(), "All Employee fetched successfully", employeeList));
	}
}
