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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String month; // e.g., "October 2025"
    private LocalDate generatedDate;
    private Double grossSalary;
    private Double deductions;
    private Double netSalary;
    private String pdfPath; // file path where the PDF is saved

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
