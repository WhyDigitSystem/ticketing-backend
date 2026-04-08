/*
 * ========================================================================
 * This file is the intellectual property of GSM Outdoors.it
 * may not be copied in whole or in part without the express written
 * permission of GSM Outdoors.
 * ========================================================================
 * Copyrights(c) 2023 GSM Outdoors. All rights reserved.
 * ========================================================================
 */
package com.base.basesetup.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base.basesetup.common.CommonConstant;
import com.base.basesetup.common.UserConstants;
import com.base.basesetup.dto.AssignTicketDTO;
import com.base.basesetup.dto.ChangePasswordFormDTO;
import com.base.basesetup.dto.LoginFormDTO;
import com.base.basesetup.dto.ResetPasswordFormDTO;
import com.base.basesetup.dto.ResponseDTO;
import com.base.basesetup.dto.SignUpFormDTO;
import com.base.basesetup.dto.UserCountDTO;
import com.base.basesetup.entity.TicketVO;
import com.base.basesetup.entity.UserVO;
import com.base.basesetup.service.UserService;
@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {
	public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<ResponseDTO> signup(@Valid @RequestBody SignUpFormDTO signUpRequest) {
		String methodName = "signup()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			userService.signup(signUpRequest);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_NAME, methodName, signUpRequest.getEmail(),
					errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, UserConstants.SIGNUP_REGISTERED_SUCCESS_MESSAGE);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, UserConstants.SIGNUP_REGISTERED_FAILED_MESSAGE,
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/login")
	public ResponseEntity<ResponseDTO> login(@Valid @RequestBody LoginFormDTO loginRequest) {
		String methodName = "login()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		UserVO userVO = null;
		try {
			userVO = userService.login(loginRequest);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_NAME, methodName, loginRequest.getUserName(),
					errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, UserConstants.USER_LOGIN_SUCCESS_MESSAGE);
			responseObjectsMap.put(UserConstants.KEY_USER_VO, userVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg,
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/logout")
	public ResponseEntity<ResponseDTO> logout(@RequestParam String userName) {
		String methodName = "logout()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			userService.logout(userName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_NAME, methodName, userName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, UserConstants.USER_LOGOUT_SUCCESS_MESSAGE);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, UserConstants.USER_LOGOUT_FAILED_MESSAGE,
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/changePassword")
	public ResponseEntity<ResponseDTO> changePassword(@Valid @RequestBody ChangePasswordFormDTO changePasswordRequest) {
		String methodName = "changePassword()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			userService.changePassword(changePasswordRequest);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_NAME, methodName,
					changePasswordRequest.getUserName(), errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, UserConstants.CHANGE_PASSWORD_SUCCESS_MESSAGE);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, UserConstants.CHANGE_PASSWORD_FAILED_MESSAGE,
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/resetPassword")
	public ResponseEntity<ResponseDTO> resetPassword(@Valid @RequestBody ResetPasswordFormDTO resetPasswordRequest) {
		String methodName = "resetPassword()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			userService.resetPassword(resetPasswordRequest);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_NAME, methodName,
					resetPasswordRequest.getUserName(), errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, UserConstants.RESET_PASSWORD_SUCCESS_MESSAGE);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, UserConstants.RESET_PASSWORD_FAILED_MESSAGE,
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getUserById")
	public ResponseEntity<ResponseDTO> getUserById(@RequestParam Long userId) {
		String methodName = "getUserById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		UserVO userVO = null;
		try {
			userVO = userService.getUserById(userId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_ID, methodName, userId, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, UserConstants.GET_USER_INFORMATION_SUCCESS_MESSAGE);
			responseObjectsMap.put(UserConstants.KEY_USER_VO, userVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					UserConstants.GET_USER_INFORMATION_FAILED_MESSAGE, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/removeUser")
	public ResponseEntity<ResponseDTO> removeUser(@RequestParam String userName) {
		String methodName = "removeUser()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			userService.removeUser(userName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_NAME, methodName, userName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, UserConstants.USER_REMOVED_SUCCESS_MESSAGE);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, UserConstants.USER_REMOVE_FAILED_MESSAGE,
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	  @GetMapping("/getEmployeeAndCustomerCount")
	    public ResponseEntity<ResponseDTO> getEmployeeAndCustomerCount() {
	        String methodName = "getEmployeeAndCustomerCount()";
	        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
	        String errorMsg = null;
	        Map<String, Object> responseObjectsMap = new HashMap<>();
	        ResponseDTO responseDTO = null;
	        try {
	            UserCountDTO userCountDTO = userService.getEmployeeAndCustomerCount();
	            responseObjectsMap.put("userCount", userCountDTO);
	        } catch (Exception e) {
	            errorMsg = e.getMessage();
	            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_ID, methodName, errorMsg);
	        }
	        if (StringUtils.isBlank(errorMsg)) {
	            responseObjectsMap.put(CommonConstant.STRING_MESSAGE,"successfullly counted");
	            responseDTO = createServiceResponse(responseObjectsMap);
	        } else {
				responseDTO = createServiceResponseError(responseObjectsMap, "Unable to Get count Information", errorMsg);
			}
	        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	        return ResponseEntity.ok().body(responseDTO);
	    }
	  
	  @GetMapping("/getAllCustomer")
		public ResponseEntity<ResponseDTO> getAllCustomer() {
			String methodName = "getAllCustomer()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<UserVO> customers = null;
			try {
				customers = userService.getAllCustomer();
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_ID, methodName, errorMsg);
			}
			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "All Customer");
				responseObjectsMap.put("usersVO", customers);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, "Unable to Get Customer Information", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
	  
	  @PutMapping("/updateCustomer")
		public ResponseEntity<ResponseDTO> updateCustomer(@RequestBody SignUpFormDTO signUpFormDTO,@RequestParam Long userId) {
			String methodName = "updateCustomer()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			try {
				UserVO userVO = userService.updateCustomer(signUpFormDTO, userId);
				if (userVO != null) {
					responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customer Update successfully");
					responseObjectsMap.put("userVO", userVO);
					responseDTO = createServiceResponse(responseObjectsMap);
				} else {
					errorMsg = "Customer not found for ID: " + userId;
					responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
				}
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error("Customer Update Failed", methodName, errorMsg);
				responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
}
