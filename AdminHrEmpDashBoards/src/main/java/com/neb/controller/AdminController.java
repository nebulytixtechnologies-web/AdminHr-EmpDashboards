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

/**
 * AdminController handles all administrative operations for the AdminHrEmpDashBoards system.
 * 
 * This includes:
 *  -- Admin login
 *  -- HR employee management
 *  -- Work assignment and tracking
 *  -- Admin add HR Detail
 * 
 * All endpoints are CORS-enabled for access from the frontend at http://localhost:5173.
 */
@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	 /**
     * Handles admin login requests.
     * 
     * @param loginReq Contains admin login credentials (email and password).
     * @return Response with admin details and login success message.
     */
	@PostMapping("/login")
	public ResponseEntity<ResponseMessage<EmployeeResponseDto>> login(@RequestBody LoginRequestDto loginReq){
		
		EmployeeResponseDto loginRes = adminService.login(loginReq);
		
		return ResponseEntity.ok(new ResponseMessage<EmployeeResponseDto>(HttpStatus.OK.value(), HttpStatus.OK.name(), "admin login successfully", loginRes));
	}
	
	/**
     * Adds a new HR employee to the system.
     * 
     * @param addEmpReq Contains HR details such as name, email, and role.
     * @return Response confirming successful addition of HR.
     */
	//for adding Hr
	@PostMapping("/addhr")
	public ResponseEntity<ResponseMessage<AddEmployeeResponseDto>> addEmployee(@RequestBody AddEmployeeRequestDto addEmpReq){
		
		AddEmployeeResponseDto addEmpRes = adminService.addEmployee(addEmpReq);
		
		return ResponseEntity.ok(new ResponseMessage<AddEmployeeResponseDto>(HttpStatus.OK.value(), HttpStatus.OK.name(), "Hr added successfully", addEmpRes));
	}
	
	/**
     * Fetches a list of all employees in the organization.
     * 
     * @return Response containing a list of employee details.
     */
	//get employee list
	@GetMapping("/getEmpList")
	public ResponseEntity<ResponseMessage<List<EmployeeDetailsResponseDto>>> getEmployeeList(){
		
		List<EmployeeDetailsResponseDto> employeeList = adminService.getEmployeeList();
		
		return ResponseEntity.ok(new ResponseMessage<List<EmployeeDetailsResponseDto>>(HttpStatus.OK.value(), HttpStatus.OK.name(), "All Employee fetched successfully", employeeList));
	}
	
	/**
     * Adds new work or task details for employees.
     * 
     * @param dto Contains details about the work to be assigned.
     * @return Response confirming successful work addition.
     */
	 @PostMapping("/work/add")
    public ResponseEntity<ResponseMessage<WorkResponseDto>> addWork(@RequestBody AddWorkRequestDto dto) {
        
        WorkResponseDto workRes = adminService.assignWork(dto);

        return ResponseEntity.ok(
            new ResponseMessage<>(HttpStatus.OK.value(), HttpStatus.OK.name(), "Work added successfully", workRes)
        );
    }
	 
	     /**
	     * Assigns work to an employee.
	     * 
	     * @param req Work assignment request containing employee ID and task details.
	     * @return Response confirming work assignment success.
	     */
	  // ✅ Assign Work
	    @PostMapping("/assignWork")
	    public ResponseEntity<ResponseMessage<WorkResponseDto>> assignWork(@RequestBody AddWorkRequestDto req) {
	        WorkResponseDto work = adminService.assignWork(req);
	        return ResponseEntity.ok(new ResponseMessage<>(200, "OK", "Work assigned successfully", work));
	    }
        
	    /**
	     * Retrieves all assigned work across all employees.
	     * 
	     * @return Response containing a list of all work entries.
	     */
	    // ✅ Get all Work
	    @GetMapping("/getAllWork")
	    public ResponseEntity<ResponseMessage<List<WorkResponseDto>>> getAllWork() {
	        List<WorkResponseDto> works = adminService.getAllWorks();
	        return ResponseEntity.ok(new ResponseMessage<>(200, "OK", "All work fetched successfully", works));
	    }
	    
	    /**
	     * Retrieves all work assigned to a specific employee.
	     * 
	     * @param empId The ID of the employee whose work is to be fetched.
	     * @return Response containing a list of work assigned to the employee.
	     */
	    // ✅ Get Employee Work
	    @GetMapping("/getWork/{empId}")
	    public ResponseEntity<ResponseMessage<List<WorkResponseDto>>> getWorkByEmployee(@PathVariable Long empId) {
	        List<WorkResponseDto> works = adminService.getWorkByEmployee(empId);
	        return ResponseEntity.ok(new ResponseMessage<>(200, "OK", "Work fetched for employee", works));
	    }
	 
}
