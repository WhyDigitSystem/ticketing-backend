package com.base.basesetup.service;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.base.basesetup.common.UserConstants;
import com.base.basesetup.dto.CreateEmployeeDTO;
import com.base.basesetup.entity.EmployeeVO;
import com.base.basesetup.entity.UserActionVO;
import com.base.basesetup.entity.UserVO;
import com.base.basesetup.exception.ApplicationException;
import com.base.basesetup.repo.EmployeeRepo;
import com.base.basesetup.repo.UserActionRepo;
import com.base.basesetup.repo.UserRepo;
import com.base.basesetup.util.CryptoUtils;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	public static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	EmployeeRepo employeeRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	UserActionRepo userActionRepo;
	
	@Override
	public EmployeeVO createEmployee(CreateEmployeeDTO createEmployeeDTO) throws Exception {
		EmployeeVO employeeVO = new EmployeeVO();
		if(ObjectUtils.isNotEmpty(createEmployeeDTO)) {
			employeeVO.setEmployee(createEmployeeDTO.getEmployee());
			if (employeeRepo.existsByCode(createEmployeeDTO.getCode())) {
				throw new ApplicationException("Employee Code Already Exist");
			}
			employeeVO.setCode(createEmployeeDTO.getCode());
			employeeVO.setGender(createEmployeeDTO.getGender());
			employeeVO.setBranch(createEmployeeDTO.getBranch());
			employeeVO.setDepartment(createEmployeeDTO.getDepartment());
			employeeVO.setDesignation(createEmployeeDTO.getDesignation());
			employeeVO.setDob(createEmployeeDTO.getDob());
			employeeVO.setDoj(createEmployeeDTO.getDoj());
			employeeVO.setEmail(createEmployeeDTO.getEmail());
			try {
				employeeVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(createEmployeeDTO.getPassword())));
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_ENCODE_USER_PASSWORD);
			}
			employeeVO.setActive(createEmployeeDTO.isActive());
			employeeVO.setCreatedBy(createEmployeeDTO.getCreatedBy());
			employeeVO.setModifiedBy(createEmployeeDTO.getModifiedBy());
			employeeRepo.save(employeeVO);
			
			UserVO userVO=new UserVO();
			if(userRepo.existsByUserName(employeeVO.getCode()))
			{
				throw new ApplicationException("Employee Code Already Exist");
			}
			userVO.setUserName(employeeVO.getCode());
			userVO.setType(employeeVO.getRole());
			userVO.setActive(employeeVO.isActive());
			userVO.setFirstName(employeeVO.getEmployee());
			try {
				userVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(createEmployeeDTO.getPassword())));
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_ENCODE_USER_PASSWORD);
			}
			createUserAction(userVO.getUserName(), userVO.getUserId(), UserConstants.USER_ACTION_ADD_ACCOUNT);
			userRepo.save(userVO);
		}
		return employeeRepo.save(employeeVO);	
	}

	private void createUserAction(String userName, Long userId, String actionType) {
		try {
			UserActionVO userActionVO = new UserActionVO();
			userActionVO.setUserName(userName);
			userActionVO.setUserId(userId);
			userActionVO.setActionType(actionType);
			userActionRepo.save(userActionVO);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		
	}

	@Override
	public List<EmployeeVO> getAllEmployee() {
		return employeeRepo.findAll();
	}

	@Override
	public EmployeeVO updateEmployee(CreateEmployeeDTO createEmployeeDTO) throws Exception {
		EmployeeVO employeeVO=new EmployeeVO();
		if(createEmployeeDTO.getId()!=0)
		{
			 employeeVO = employeeRepo.findById(createEmployeeDTO.getId()).get();
			if(!employeeVO.getCode().equals(createEmployeeDTO.getCode()))
			{
				if(employeeRepo.existsByCode(createEmployeeDTO.getCode()))
				{
					throw new ApplicationException("Employee Code Already Exist");
				}
				employeeVO.setCode(createEmployeeDTO.getCode());
			}
			employeeVO.setEmployee(createEmployeeDTO.getEmployee());
			employeeVO.setGender(createEmployeeDTO.getGender());
			employeeVO.setBranch(createEmployeeDTO.getBranch());
			employeeVO.setDepartment(createEmployeeDTO.getDepartment());
			employeeVO.setDesignation(createEmployeeDTO.getDesignation());
			employeeVO.setDob(createEmployeeDTO.getDob());
			employeeVO.setDoj(createEmployeeDTO.getDoj());
			employeeVO.setEmail(createEmployeeDTO.getEmail());
			try {
				employeeVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(createEmployeeDTO.getPassword())));
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_ENCODE_USER_PASSWORD);
			}
			employeeVO.setActive(createEmployeeDTO.isActive());
			employeeVO.setModifiedBy(createEmployeeDTO.getModifiedBy());
			employeeRepo.save(employeeVO);
			
			UserVO userVO = userRepo.findByUserName(employeeVO.getCode());
			userVO.setUserName(employeeVO.getCode());
			userVO.setType(employeeVO.getRole());
			userVO.setActive(employeeVO.isActive());
			userVO.setFirstName(employeeVO.getEmployee());
			try {
				userVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(createEmployeeDTO.getPassword())));
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_ENCODE_USER_PASSWORD);
			}
			createUserAction(userVO.getUserName(), userVO.getUserId(), UserConstants.USER_ACTION_ADD_ACCOUNT);
			userRepo.save(userVO);
		}
		
		return employeeVO;
	}

	
}
