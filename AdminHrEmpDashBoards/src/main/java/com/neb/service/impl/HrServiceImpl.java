package com.neb.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neb.dto.AddEmployeeRequestDto;
import com.neb.dto.AddEmployeeResponseDto;
import com.neb.dto.EmployeeDetailsResponseDto;
import com.neb.dto.EmployeeResponseDto;
import com.neb.dto.LoginRequestDto;
import com.neb.dto.PayslipDto;
import com.neb.entity.Employee;
import com.neb.entity.Payslip;
import com.neb.exception.CustomeException;
import com.neb.repository.EmployeeRepository;
import com.neb.repository.PayslipRepository;
import com.neb.service.HrService;

@Service
public class HrServiceImpl implements HrService{

	@Autowired
    private EmployeeRepository empRepo;
	
	@Autowired
	private PayslipRepository payslipRepo;

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
        ).orElseThrow(() -> new CustomeException("Invalid credentials. Please check your email and password and login role"));

        // map entity to DTO
        EmployeeResponseDto loginRes = mapper.map(emp, EmployeeResponseDto.class);

        return loginRes;
    }
    
	// --------- ADD EMPLOYEE ----------
    @Override
    public AddEmployeeResponseDto addEmployee(AddEmployeeRequestDto addEmpReq) {

        // check if employee with same email already exists
        if (empRepo.existsByEmail(addEmpReq.getEmail())) {
        	 throw new CustomeException("Employee with email " + addEmpReq.getEmail() + " already exists");
        }

        // map DTO to entity
       
        Employee emp = mapper.map(addEmpReq, Employee.class);

        // save entity
        Employee savedEmp = empRepo.save(emp);
      

        // map saved entity to response DTO
        AddEmployeeResponseDto addEmpRes = mapper.map(savedEmp, AddEmployeeResponseDto.class);

        return addEmpRes;
    }
    @Override
    public List<EmployeeDetailsResponseDto> getEmployeeList() {
		
		//getting all employee list without admin
	    List<Employee> employeeList = empRepo.findByLoginRoleNotIn(List.of("admin","hr"));
	    
	    if(employeeList==null) {
	    	 throw new CustomeException("Employees list not found");
	    }
	    
	    List<EmployeeDetailsResponseDto> empListRes = employeeList.stream().map(emp->{
	    	
	    	EmployeeDetailsResponseDto empResDto = mapper.map(emp, EmployeeDetailsResponseDto.class);
	    	return empResDto;
	    }).collect(Collectors.toList());
	    
	    return empListRes;
	}

	@Override
	public EmployeeDetailsResponseDto getEmployee(Long id) {

		Employee emp = empRepo.findById(id).orElseThrow(()->new CustomeException("Employee not founce wuith id :"+id));
		return mapper.map(emp, EmployeeDetailsResponseDto.class);
		
	}

	@Override
	public String deleteById(Long id) {
		empRepo.deleteById(id);
		return id+" Employee Deleted Successfully";
	}
	
	 //downloading payslip using payslip id
	@Override
	 public byte[] downloadPayslip(Long payslipId) throws Exception {
        Payslip p = payslipRepo.findById(payslipId)
            .orElseThrow(() -> new CustomeException("Payslip not found"));

        Path path = Paths.get(p.getPdfPath());
        return Files.readAllBytes(path);
    }
	 
	 //getting list of payslips of employee using employee id
	@Override
     public List<PayslipDto> listPayslipsForEmployee(Long employeeId) {
        List<Payslip> payslips = payslipRepo.findByEmployeeId(employeeId);
        
        if(payslips==null) {
        	throw new CustomeException("payslip list is not found with employeeId: "+employeeId);
        }
        List<PayslipDto> paySlipDtos = payslips.stream()
                                        .map(PayslipDto::fromEntity)
                                        .toList();
        return paySlipDtos;
    }

	@Override
	public EmployeeDetailsResponseDto addAttendence(Long id, int days) {
		
		Employee emp = empRepo.findById(id).orElseThrow(()->new CustomeException("employee not found with id:"+id));
		emp.setDaysPresent(days);
		Employee savedemp = empRepo.save(emp);
		EmployeeDetailsResponseDto updateEmpDto= mapper.map(savedemp, EmployeeDetailsResponseDto.class);
		return updateEmpDto;
	}

}
