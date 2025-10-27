package com.neb.controller;

//Original Admin
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neb.dto.AddEmployeeRequestDto;
import com.neb.dto.AddEmployeeResponseDto;
import com.neb.dto.AddWorkRequestDto;
import com.neb.dto.EmployeeDetailsResponseDto;
import com.neb.dto.EmployeeResponseDto;
import com.neb.dto.LoginRequestDto;
import com.neb.dto.ResponseMessage;
import com.neb.dto.WorkResponseDto;
import com.neb.service.AdminService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@PostMapping("/login")
	public ResponseEntity<ResponseMessage<EmployeeResponseDto>> login(@RequestBody LoginRequestDto loginReq){
		
		EmployeeResponseDto loginRes = adminService.login(loginReq);
		
		return ResponseEntity.ok(new ResponseMessage<EmployeeResponseDto>(HttpStatus.OK.value(), HttpStatus.OK.name(), "admin login successfully", loginRes));
	}
	
	//for adding Hr
	@PostMapping("/add")
	public ResponseEntity<ResponseMessage<AddEmployeeResponseDto>> addEmployee(@RequestBody AddEmployeeRequestDto addEmpReq){
		
		AddEmployeeResponseDto addEmpRes = adminService.addEmployee(addEmpReq);
		
		return ResponseEntity.ok(new ResponseMessage<AddEmployeeResponseDto>(HttpStatus.OK.value(), HttpStatus.OK.name(), "Hr added successfully", addEmpRes));
	}
	
	//get employee list
	@GetMapping("/getEmpList")
	public ResponseEntity<ResponseMessage<List<EmployeeDetailsResponseDto>>> getEmployeeList(){
		
		List<EmployeeDetailsResponseDto> employeeList = adminService.getEmployeeList();
		
		return ResponseEntity.ok(new ResponseMessage<List<EmployeeDetailsResponseDto>>(HttpStatus.OK.value(), HttpStatus.OK.name(), "All Employee fetched successfully", employeeList));
	}
	
	
	 @PostMapping("/work/add")
    public ResponseEntity<ResponseMessage<WorkResponseDto>> addWork(@RequestBody AddWorkRequestDto dto) {
        
        WorkResponseDto workRes = adminService.assignWork(dto);

        return ResponseEntity.ok(
            new ResponseMessage<>(HttpStatus.OK.value(), HttpStatus.OK.name(), "Work added successfully", workRes)
        );
    }
	  // ✅ Assign Work
	    @PostMapping("/assignWork")
	    public ResponseEntity<ResponseMessage<WorkResponseDto>> assignWork(@RequestBody AddWorkRequestDto req) {
	        WorkResponseDto work = adminService.assignWork(req);
	        return ResponseEntity.ok(new ResponseMessage<>(200, "OK", "Work assigned successfully", work));
	    }

	    // ✅ Get all Work
	    @GetMapping("/getAllWork")
	    public ResponseEntity<ResponseMessage<List<WorkResponseDto>>> getAllWork() {
	        List<WorkResponseDto> works = adminService.getAllWorks();
	        return ResponseEntity.ok(new ResponseMessage<>(200, "OK", "All work fetched successfully", works));
	    }

	    // ✅ Get Employee Work
	    @GetMapping("/getWork/{empId}")
	    public ResponseEntity<ResponseMessage<List<WorkResponseDto>>> getWorkByEmployee(@PathVariable Long empId) {
	        List<WorkResponseDto> works = adminService.getWorkByEmployee(empId);
	        return ResponseEntity.ok(new ResponseMessage<>(200, "OK", "Work fetched for employee", works));
	    }
	 
}
