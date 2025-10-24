package com.neb.entity;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "payslips")
@Data
public class Payslip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "salary_month") // avoid reserved keyword
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
