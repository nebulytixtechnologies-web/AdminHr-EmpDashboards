package com.neb.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EmployeeDetailsResponseDto {

	private Long id;
	private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String jobRole;     // intern/developer/cloud engineer/hr
    private String domain;      // Java / .Net / Python
    private String gender;
    private LocalDate joiningDate;
    private Double salary;
    private int daysPresent;
    private int paidLeaves;
    
}
