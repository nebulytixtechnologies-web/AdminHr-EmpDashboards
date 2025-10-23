package com.neb.entity;

import java.time.LocalDate;

import com.neb.constants.WorkStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "work")
@Data
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Task Details
    private String title;
    private String description;
    private LocalDate assignedDate;
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private WorkStatus status; // ASSIGNED, IN_PROGRESS, COMPLETED, REPORTED

    // Report Details
    private String reportDetails;
    private LocalDate submittedDate;
    private String attachmentUrl; // Optional

    // Relations
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
