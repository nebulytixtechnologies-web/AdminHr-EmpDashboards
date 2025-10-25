package com.neb.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.neb.dto.EmployeeResponseDto;
import com.neb.dto.GeneratePayslipRequest;
import com.neb.dto.LoginRequestDto;
import com.neb.dto.PayslipDto;
import com.neb.dto.ResponseMessage;
import com.neb.entity.Payslip;
import com.neb.service.EmployeeService;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin(origins = "http://localhost:5173")
public class EmployeeController {

	@Autowired
	private EmployeeService service;
	
	@PostMapping("/login")
	public ResponseEntity<ResponseMessage<EmployeeResponseDto>> login(@RequestBody LoginRequestDto loginReq){
		
		EmployeeResponseDto loginRes = service.login(loginReq);
		
		return ResponseEntity.ok(new ResponseMessage<EmployeeResponseDto>(HttpStatus.OK.value(), HttpStatus.OK.name(), "Employee login successfully", loginRes));
	}
	
	
	 @PostMapping("/payslip/generate")
    public ResponseEntity<PayslipDto> generate(@RequestBody GeneratePayslipRequest request) throws Exception {
        Payslip p = service.generatePayslip(request.getEmployeeId(), request.getMonthYear());
        PayslipDto dto = PayslipDto.fromEntity(p);
        return ResponseEntity.ok(dto);
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
	  
	 
	
	
}
