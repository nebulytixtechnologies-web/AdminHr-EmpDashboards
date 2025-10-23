package com.neb.dto;

import lombok.Data;

@Data
public class AddEmployeeResponseDto {
    private Long id;
    private String firstName;
    private String email;
    private String loginRole;
    private String jobRole;
    private String password;
}
