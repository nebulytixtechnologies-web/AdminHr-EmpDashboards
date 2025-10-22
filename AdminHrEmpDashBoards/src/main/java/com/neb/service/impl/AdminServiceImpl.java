package com.neb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neb.repository.AdminHrRepository;
import com.neb.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminHrRepository adminHrRepo;
}
