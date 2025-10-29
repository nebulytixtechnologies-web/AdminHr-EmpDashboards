/**
 * --------------------------------------------------------------
 * Purpose :
 *   Represents the Admin and HR users who can log in to the system.
 *
 * Description :
 *   - Stores login details like email, password, and role.
 *   - Role can be ADMIN, HR, or EMPLOYEE.
 *   - Each mail ID must be unique.
 * --------------------------------------------------------------
 */

package com.neb.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class AdminAndHr {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    @NonNull
    private String mailId;   // Unique email ID for login

    @Column(nullable = false)
    @NonNull
    private String password; // User password

    @Column(nullable = false)
    @NonNull
    private String role;     // Role type (ADMIN, HR, EMPLOYEE)
}
