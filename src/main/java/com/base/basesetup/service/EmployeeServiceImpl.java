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
public class EmployeeServiceImpl implements EmployeeService {

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
		if (ObjectUtils.isNotEmpty(createEmployeeDTO)) {
			employeeVO.setEmployee(createEmployeeDTO.getEmployee());
			if (employeeRepo.existsByEmail(createEmployeeDTO.getEmail())) {
				throw new ApplicationException("Employee Email Already Exist");
			}
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

			UserVO userVO = new UserVO();
			if (userRepo.existsByUserName(employeeVO.getEmail())) {
				throw new ApplicationException("Employee Email Already Exist");
			}
			userVO.setUserName(employeeVO.getEmail());
			userVO.setType(employeeVO.getRole());
			userVO.setActive(employeeVO.isActive());
			userVO.setEmail(employeeVO.getEmail());
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

		 if (createEmployeeDTO.getId() == 0) {
		        throw new ApplicationException("Invalid Employee ID");
		    }

		    // 🔹 Fetch employee
		    EmployeeVO employeeVO = employeeRepo.findById(createEmployeeDTO.getId())
		            .orElseThrow(() -> new ApplicationException("Employee Not Found"));

		    // 🔹 Store old email BEFORE update
		    String oldEmail = employeeVO.getEmail();

		    // 🔹 Code validation
		    if (!employeeVO.getCode().equals(createEmployeeDTO.getCode())) {
		        if (employeeRepo.existsByCode(createEmployeeDTO.getCode())) {
		            throw new ApplicationException("Employee Code Already Exist");
		        }
		        employeeVO.setCode(createEmployeeDTO.getCode());
		    }

		    // 🔹 Email validation
		    if (!employeeVO.getEmail().equals(createEmployeeDTO.getEmail())) {
		        if (employeeRepo.existsByEmail(createEmployeeDTO.getEmail())) {
		            throw new ApplicationException("Employee Email Already Exist");
		        }
		        employeeVO.setEmail(createEmployeeDTO.getEmail());
		    }

		    // 🔹 Other fields update
		    employeeVO.setEmployee(createEmployeeDTO.getEmployee());
		    employeeVO.setGender(createEmployeeDTO.getGender());
		    employeeVO.setBranch(createEmployeeDTO.getBranch());
		    employeeVO.setDepartment(createEmployeeDTO.getDepartment());
		    employeeVO.setDesignation(createEmployeeDTO.getDesignation());
		    employeeVO.setDob(createEmployeeDTO.getDob());
		    employeeVO.setDoj(createEmployeeDTO.getDoj());
		    employeeVO.setActive(createEmployeeDTO.isActive());
		    employeeVO.setModifiedBy(createEmployeeDTO.getModifiedBy());

		    // 🔹 Save employee
		    employeeRepo.save(employeeVO);

		    // 🔹 Fetch user using OLD email (safe fallback)
		    UserVO userVO = userRepo.findByUserName(oldEmail);

		    if (userVO == null) {
		        // 🔥 fallback if mismatch exists in DB
		        userVO = userRepo.findByEmail(oldEmail);
		    }

		    if (userVO == null) {
		        throw new ApplicationException("User Not Found for old email: " + oldEmail);
		    }


		userVO.setUserName(employeeVO.getEmail());
		userVO.setType(employeeVO.getRole());
		userVO.setActive(employeeVO.isActive());
		userVO.setFirstName(employeeVO.getEmployee());
		userVO.setEmail(employeeVO.getEmail());
		userVO.setCompany("EFIT");
//			try {
//				userVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(createEmployeeDTO.getPassword())));
//			} catch (Exception e) {
//				LOGGER.error(e.getMessage());
//				throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_ENCODE_USER_PASSWORD);
//			}
		createUserAction(userVO.getUserName(), userVO.getUserId(), UserConstants.USER_ACTION_ADD_ACCOUNT);
		userRepo.save(userVO);

		return employeeVO;
	}

}
