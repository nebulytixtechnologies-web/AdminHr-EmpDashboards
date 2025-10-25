package com.neb.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neb.dto.AddEmployeeRequestDto;
import com.neb.dto.AddEmployeeResponseDto;
import com.neb.dto.AddWorkRequestDto;
import com.neb.dto.EmployeeDetailsResponseDto;
import com.neb.dto.EmployeeResponseDto;
import com.neb.dto.LoginRequestDto;
import com.neb.dto.WorkResponseDto;
import com.neb.entity.Employee;
import com.neb.entity.Work;
import com.neb.repository.EmployeeRepository;
import com.neb.repository.WorkRepository;
import com.neb.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
    private EmployeeRepository empRepo;
	
	@Autowired
    private WorkRepository workRepo;

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
        ).orElseThrow(() -> new RuntimeException("Invalid credentials"+loginReq));
       
        // map entity to DTO
        EmployeeResponseDto loginRes = mapper.map(emp, EmployeeResponseDto.class);

        return loginRes;
    }
    
    
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

 //  ----------Get Employee List-------------
	@Override
	public List<EmployeeDetailsResponseDto> getEmployeeList() {
		
		//getting all employee list without admin
	    List<Employee> employeeList = empRepo.findByLoginRoleNot("admin");
	    
	    //to-do handling if employee not found
	    employeeList.forEach(System.out::println);
	    
	    List<EmployeeDetailsResponseDto> empListRes = employeeList.stream().map(emp->{
	    	
	    	EmployeeDetailsResponseDto empResDto = mapper.map(emp, EmployeeDetailsResponseDto.class);
	    	return empResDto;
	    }).collect(Collectors.toList());
	    
	    return empListRes;
	}
	
//............. adding work ..............
	@Override
	public WorkResponseDto createWork(AddWorkRequestDto dto) {
        // find the employee
        Employee employee = empRepo.findById(dto.getEmployeeId())
            .orElseThrow(() -> new RuntimeException("Employee not found with id: " + dto.getEmployeeId()));

        Work work = new Work();
        work.setTitle(dto.getTitle());
        work.setDescription(dto.getDescription());
        work.setAssignedDate(dto.getAssignedDate());
        work.setDueDate(dto.getDueDate());
        work.setStatus(dto.getStatus());
        work.setReportDetails(dto.getReportDetails());
        work.setSubmittedDate(dto.getSubmittedDate());
        work.setEmployee(employee);
        
        Work savedWork = workRepo.save(work);
        
        WorkResponseDto workDtoRes = mapper.map(savedWork, WorkResponseDto.class);

        return workDtoRes;
    }
}
