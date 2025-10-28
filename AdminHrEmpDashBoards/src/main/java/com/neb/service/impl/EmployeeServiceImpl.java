package com.neb.service.impl;
//original
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.neb.dto.EmployeeDetailsResponseDto;
import com.neb.dto.EmployeeResponseDto;
import com.neb.dto.LoginRequestDto;
import com.neb.entity.Employee;
import com.neb.entity.Payslip;
import com.neb.entity.Work;
import com.neb.exception.CustomeException;
import com.neb.repository.EmployeeRepository;
import com.neb.repository.PayslipRepository;
import com.neb.repository.WorkRepository;
import com.neb.service.EmployeeService;
import com.neb.util.PdfGeneratorUtil;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository empRepo;
    
    @Autowired
    private PayslipRepository payslipRepo;

    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private WorkRepository workRepository;

    
    @Value("${payslip.base-folder}")
    private String baseFolder;

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
    //Getting employee By ID
    public Employee getEmployeeById(Long id) {
        return empRepo.findById(id).orElseThrow(() -> new CustomeException("Employee not found with id: "+id));
    }
    
	@Override
	public Payslip generatePayslip(Long employeeId, String monthYear) throws Exception{
		
		
		Employee emp = empRepo.findById(employeeId)
	            .orElseThrow(() -> new CustomeException("Employee not found with id: "+employeeId));
		
		Payslip p = new Payslip();
        p.setEmployee(emp);
        p.setPayslipMonth(monthYear);
        p.setGeneratedDate(LocalDate.now());
        p.setLocation("FLAT NO 501B,PSR PRIME TOWERS,BESIDE DLF,GACHIBOWLI,500032");

        double salary = emp.getSalary();
        p.setBasic(salary * 0.53);
        p.setHra(salary * 0.20);
        p.setFlexi(salary * 0.27);
        double gross = p.getBasic() + p.getHra() + p.getFlexi();//
        p.setGrossSalary(gross);

        p.setPfDeduction(p.getBasic() * 0.12);
        p.setProfTaxDeduction(200.0);
        double ded = p.getPfDeduction() + p.getProfTaxDeduction();
        p.setTotalDeductions(ded);

        double net = gross - ded;
        p.setNetSalary(net);
        
        p.setBalance(gross);
        p.setAggrgDeduction(ded);
        p.setIncHdSalary(net);
        p.setTaxCredit(net*0.05);//random values added

        p = payslipRepo.save(p);
        
        String fileName = "payslip_" + emp.getId() + "_" + monthYear.replace(" ", "_") + ".pdf";
        String folderPath = baseFolder + "/" + monthYear.replace(" ", "_");
        Files.createDirectories(Paths.get(folderPath));
        String fullPath = folderPath + "/" + fileName;

        
        byte[] pdfBytes = PdfGeneratorUtil.createPayslipPdf(emp, p);
        Files.write(Paths.get(fullPath), pdfBytes);

        p.setPdfPath(fullPath);
        p.setFileName(fileName);
        payslipRepo.save(p);

        return p;
	}
	 // Get employee details by EMAIL
    public EmployeeDetailsResponseDto getEmployeeByEmail(String email) {
    	System.out.println(email);
    	Employee emp = empRepo.findByEmail(email).orElseThrow(()->new CustomeException("Employee not found with email id :"+email));
    	EmployeeDetailsResponseDto empdetailsDto = mapper.map(emp, EmployeeDetailsResponseDto.class);
        return empdetailsDto;
    }

    public List<Work> getTasksByEmployee(Long employeeId) {
        Employee emp = getEmployeeById(employeeId);
        List<Work> workListbyEmployee = workRepository.findByEmployee(emp);
        if(workListbyEmployee==null) {
        	throw new CustomeException("work list is empty for employee with id: "+emp.getId());
        }
        return workListbyEmployee;
    }

    public Work submitReport(Long taskId, String reportDetails, LocalDate submittedDate) {
        Work task = workRepository.findById(taskId).orElseThrow(() -> new CustomeException("Task not found with taskId :"+taskId));
        task.setReportDetails(reportDetails);
        task.setSubmittedDate(submittedDate);
        task.setStatus(com.neb.constants.WorkStatus.COMPLETED);
        return workRepository.save(task);
    }
	 
}
