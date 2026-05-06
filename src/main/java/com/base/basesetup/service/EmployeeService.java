package com.base.basesetup.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.base.basesetup.dto.CreateEmployeeDTO;
import com.base.basesetup.entity.EmployeeVO;

@Service
public interface EmployeeService {

	// Create Ticket from User
	
	EmployeeVO createEmployee(CreateEmployeeDTO createEmployeeDTO) throws Exception;
	
	List<EmployeeVO> getAllEmployee();
	
	EmployeeVO updateEmployee(CreateEmployeeDTO createEmployeeDTO) throws Exception;
}
