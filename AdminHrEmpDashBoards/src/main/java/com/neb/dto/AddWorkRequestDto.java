package com.neb.dto;

import java.time.LocalDate;

import com.neb.constants.WorkStatus;

import lombok.Data;

@Data
public class AddWorkRequestDto {
    private String title;
    private String description;
    private LocalDate assignedDate;
    private LocalDate dueDate;
    private WorkStatus status;
    private String reportDetails;
    private LocalDate submittedDate;
    private Long employeeId;
}
