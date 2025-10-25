package com.neb.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "payslips")
@Data
public class Payslip {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String payslipMonth;  // e.g., "August 2025"
    private LocalDate generatedDate;

    // salary breakdown
    private Double basic;
    private Double hra;
    private Double flexi;
    private Double grossSalary;

    // deductions breakdown
    private Double pfDeduction;
    private Double profTaxDeduction;
    private Double totalDeductions;

    private Double netSalary;

    private String pdfPath;       
    private String fileName;       

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
