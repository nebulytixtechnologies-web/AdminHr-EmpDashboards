package com.neb.controller;
//original

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.neb.dto.EmployeeResponseDto;
import com.neb.dto.GeneratePayslipRequest;
import com.neb.dto.LoginRequestDto;
import com.neb.dto.PayslipDto;
import com.neb.dto.ResponseMessage;
import com.neb.entity.Employee;
import com.neb.entity.Payslip;
import com.neb.entity.Work;
import com.neb.service.EmployeeService;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin(origins = "http://localhost:5173")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping("/login")
	public ResponseEntity<ResponseMessage<EmployeeResponseDto>> login(@RequestBody LoginRequestDto loginReq){
		
		EmployeeResponseDto loginRes = employeeService.login(loginReq);
		
		return ResponseEntity.ok(new ResponseMessage<EmployeeResponseDto>(HttpStatus.OK.value(), HttpStatus.OK.name(), "Employee login successfully", loginRes));
	}
	
	
	@PostMapping("/payslip/generate")
    public ResponseEntity<PayslipDto> generate(@RequestBody GeneratePayslipRequest request) throws Exception {
        Payslip p = employeeService.generatePayslip(request.getEmployeeId(), request.getMonthYear());
        PayslipDto dto = PayslipDto.fromEntity(p);
        return ResponseEntity.ok(dto);
    }
	 // Get employee details
    @GetMapping("/get/{id}")
    public ResponseMessage<Employee> getEmployee(@PathVariable Long id) {
        Employee emp = employeeService.getEmployeeById(id);
        return new ResponseMessage<>(HttpStatus.OK.value(), HttpStatus.OK.name(), "Employee fetched successfully", emp);
    }
    @GetMapping("/details/{email}")
    public ResponseEntity<ResponseMessage<Employee>> getEmployeeByEmail(@PathVariable String email) {
        Employee emp = employeeService.getEmployeeByEmail(email);	
        if (emp == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage<>(404, "NOT_FOUND", "Employee not found"));
        }
        return ResponseEntity.ok(
                new ResponseMessage<>(200, "OK", "Employee fetched successfully", emp)
        );
    }

    // Get tasks assigned to employee
    @GetMapping("/tasks/{employeeId}")
    public ResponseMessage<List<Work>> getTasks(@PathVariable Long employeeId) {
        List<Work> tasks = employeeService.getTasksByEmployee(employeeId);
        return new ResponseMessage<>(HttpStatus.OK.value(), HttpStatus.OK.name(), "Tasks fetched successfully", tasks);
    }

    // Submit task report
    @PutMapping("/task/submit/{taskId}")
    public ResponseMessage<Work> submitTaskReport(
            @PathVariable Long taskId,
            @RequestBody Work reportData
    ) {
        Work updatedTask = employeeService.submitReport(taskId, reportData.getReportDetails(),  LocalDate.now());
        return new ResponseMessage<>(HttpStatus.OK.value(), HttpStatus.OK.name(), "Report submitted successfully", updatedTask);
    }
    

	 	
}
