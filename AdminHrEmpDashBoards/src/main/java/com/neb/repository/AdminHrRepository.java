/**
 * ---------------------------------------------------------------
 * File Name   : AdminHrRepository.java
 * Package     : com.neb.repository
 * ---------------------------------------------------------------
 * Purpose :
 *   This interface is used to perform database operations
 *   (like save, update, delete, and find) for the Admin and HR data.
 *
 * Description :
 *   - Extends JpaRepository to get all basic CRUD operations.
 *   - Works with the AdminAndHr entity using its primary key (Long id).
 *
 * Result :
 *   Provides easy access to Admin and HR records in the database
 *   without writing SQL queries manually.
 * ---------------------------------------------------------------
 */

package com.neb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.neb.entity.AdminAndHr;

public interface AdminHrRepository extends JpaRepository<AdminAndHr, Long> {

}
