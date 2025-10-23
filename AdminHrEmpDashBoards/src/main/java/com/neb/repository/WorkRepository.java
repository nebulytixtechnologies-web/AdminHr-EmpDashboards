package com.neb.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.neb.entity.Work;

public interface WorkRepository extends JpaRepository<Work, Long> {

}
