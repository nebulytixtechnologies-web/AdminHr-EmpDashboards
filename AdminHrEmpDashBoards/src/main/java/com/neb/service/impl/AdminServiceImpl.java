package com.neb.service.impl;
import java.time.LocalDate;
//original 
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neb.constants.WorkStatus;
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
	 // ✅ Assign Task to Employee
    public WorkResponseDto assignWork(AddWorkRequestDto request) {
        Employee emp = empRepo.findById(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Work work = new Work();
        work.setTitle(request.getTitle());
        work.setDescription(request.getDescription());
        work.setAssignedDate(LocalDate.now());
        work.setDueDate(request.getDueDate());
        work.setStatus(WorkStatus.ASSIGNED);
        work.setEmployee(emp);

        workRepo.save(work);

        return mapToDto(work);
    }

    // ✅ Fetch All Work/Tasks
    public List<WorkResponseDto> getAllWorks() {
        return workRepo.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // ✅ Fetch Work by Employee
    public List<WorkResponseDto> getWorkByEmployee(Long empId) {
        return workRepo.findByEmployeeId(empId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // ✅ Helper Method
    private WorkResponseDto mapToDto(Work work) {
        WorkResponseDto dto = new WorkResponseDto();
        dto.setId(work.getId());
        dto.setTitle(work.getTitle());
        dto.setDescription(work.getDescription());
        dto.setAssignedDate(work.getAssignedDate());
        dto.setDueDate(work.getDueDate());
        dto.setStatus(work.getStatus());
        dto.setReportDetails(work.getReportDetails());
        dto.setSubmittedDate(work.getSubmittedDate());
        dto.setEmployeeId(work.getEmployee().getId());
        dto.setEmployeeName(work.getEmployee().getFirstName() + " " + work.getEmployee().getLastName());
        dto.setEmployeeEmail(work.getEmployee().getEmail());
        return dto;
    }

	}
