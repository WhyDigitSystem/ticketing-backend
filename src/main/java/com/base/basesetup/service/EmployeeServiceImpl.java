package com.base.basesetup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.basesetup.dto.CreateEmployeeDTO;
import com.base.basesetup.entity.EmployeeVO;
import com.base.basesetup.repo.EmployeeRepo;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	EmployeeRepo employeeRepo;
	
	@Override
	public EmployeeVO createEmployee(CreateEmployeeDTO createEmployeeDTO) {
		EmployeeVO employeeVO = new EmployeeVO();
		employeeVO.setEmployee(createEmployeeDTO.getEmployee());
		employeeVO.setCode(createEmployeeDTO.getCode());
		employeeVO.setDepartment(createEmployeeDTO.getDepartment());
		employeeVO.setDoj(createEmployeeDTO.getDoj());
		return employeeRepo.save(employeeVO);
	}

	@Override
	public List<EmployeeVO> getAllEmployee() {
		// TODO Auto-generated method stub
		return employeeRepo.findAll();
	}

	
}
