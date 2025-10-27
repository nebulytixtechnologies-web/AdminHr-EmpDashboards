package com.neb.controller;
//Origi
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neb.dto.AddEmployeeRequestDto;
import com.neb.dto.AddEmployeeResponseDto;
import com.neb.dto.EmployeeDetailsResponseDto;
import com.neb.dto.EmployeeResponseDto;
import com.neb.dto.GeneratePayslipRequest;
import com.neb.dto.LoginRequestDto;
import com.neb.dto.PayslipDto;
import com.neb.dto.ResponseMessage;
import com.neb.entity.Payslip;
import com.neb.service.EmployeeService;
import com.neb.service.HrService;

@RestController
@RequestMapping("/api/hr")
@CrossOrigin(origins = "http://localhost:5173")
public class HrController {

	@Autowired
	private HrService service;
	
	@Autowired
	private EmployeeService employeeService;
	
	
	@PostMapping("/login")
	public ResponseEntity<ResponseMessage<EmployeeResponseDto>> login(@RequestBody LoginRequestDto loginReq){
		
		EmployeeResponseDto loginRes = service.login(loginReq);
		
		return ResponseEntity.ok(new ResponseMessage<EmployeeResponseDto>(HttpStatus.OK.value(), HttpStatus.OK.name(), "Hr login successfully", loginRes));
	}
	
	//adding employee
	@PostMapping("/add")
	public ResponseEntity<ResponseMessage<AddEmployeeResponseDto>> addEmployee(@RequestBody AddEmployeeRequestDto addEmpReq){
		
		AddEmployeeResponseDto addEmpRes = service.addEmployee(addEmpReq);
		
		return ResponseEntity.ok(new ResponseMessage<AddEmployeeResponseDto>(HttpStatus.OK.value(), HttpStatus.OK.name(), "employee added successfully", addEmpRes));
	}
	@GetMapping("/getEmpList")
	public ResponseEntity<ResponseMessage<List<EmployeeDetailsResponseDto>>> getEmployeeList(){
		
		List<EmployeeDetailsResponseDto> employeeList = service.getEmployeeList();
		
		return ResponseEntity.ok(new ResponseMessage<List<EmployeeDetailsResponseDto>>(HttpStatus.OK.value(), HttpStatus.OK.name(), "All Employee fetched successfully", employeeList));
	}
	
	@GetMapping("/getEmp/{id}")
	public ResponseEntity<ResponseMessage<EmployeeDetailsResponseDto>> getEmployee(@PathVariable Long id){
		
		EmployeeDetailsResponseDto employee = service.getEmployee(id);
	
		
		return ResponseEntity.ok(new ResponseMessage<EmployeeDetailsResponseDto>(HttpStatus.OK.value(), HttpStatus.OK.name(), " Employee fetched successfully", employee));
	}
	@GetMapping("/delete/{id}")
	public ResponseEntity<ResponseMessage<String>> deleteEmployee(@PathVariable Long id){
		
	 String deleteById = service.deleteById(id);
	
		
		return ResponseEntity.ok(new ResponseMessage<String>(HttpStatus.OK.value(), HttpStatus.OK.name(), " Employee deleted successfully", deleteById));
	}
	
	@GetMapping("/payslip/{id}/download")
    public ResponseEntity<byte[]> download(@PathVariable Long id) throws Exception {
        byte[] pdf = service.downloadPayslip(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition
            .attachment()
            .filename("payslip_" + id + ".pdf")
            .build());

        return ResponseEntity.ok()
                             .headers(headers)
                             .body(pdf);
    }

    @GetMapping("/payslip/{employeeId}")
    public ResponseEntity<List<PayslipDto>> listPayslips(@PathVariable Long employeeId) {
        List<PayslipDto> payslips = service.listPayslipsForEmployee(employeeId);
        return ResponseEntity.ok(payslips);
    }
    
    @PostMapping("/payslip/generate")
    public ResponseEntity<PayslipDto> generate(@RequestBody GeneratePayslipRequest request) throws Exception {
        Payslip p = employeeService.generatePayslip(request.getEmployeeId(), request.getMonthYear());
        PayslipDto dto = PayslipDto.fromEntity(p);
        return ResponseEntity.ok(dto);
    }
	 
    @PutMapping("/editEmp/{empId}/{days}")
    public ResponseEntity<ResponseMessage<EmployeeDetailsResponseDto>> addAttendence(@PathVariable Long empId, @PathVariable int days){
    	
    	EmployeeDetailsResponseDto updatedEmp = service.addAttendence(empId, days);
    	
    	return ResponseEntity.ok(new ResponseMessage<EmployeeDetailsResponseDto>(HttpStatus.OK.value(), HttpStatus.OK.name(), "employee details updated", updatedEmp));
    }
	

}
