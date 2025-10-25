package com.neb.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "employees")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String mobile;

    //This is for login role (admin/hr/employee)
    private String loginRole;  
    private String jobRole; // intern/developer/cloud engineer/hr
    private String domain;//Java/.Net/Python
    private String gender;
    private LocalDate joiningDate;
    private Double salary;
    private int daysPresent;
    private int paidLeaves;
    private String password;
    
    private String bankAccountNumber;
    private String bankName;
    private String pfNumber;
    private String panNumber;
    private String uanNumber;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Work> works = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payslip> payslips = new ArrayList<>();
}
