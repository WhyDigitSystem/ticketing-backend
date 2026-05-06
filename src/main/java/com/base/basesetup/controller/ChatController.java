package com.base.basesetup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base.basesetup.common.CommonConstant;
import com.base.basesetup.common.UserConstants;
import com.base.basesetup.dto.ChatDTO;
import com.base.basesetup.dto.CreateTicketDTO;
import com.base.basesetup.dto.ResponseDTO;
import com.base.basesetup.entity.ChatVO;
import com.base.basesetup.entity.TicketVO;
import com.base.basesetup.service.ChatServices;

@RestController
public class ChatController  extends BaseController{
	
	public static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);
	
	@Autowired
	ChatServices chatServices;
	
	@GetMapping("/getAllChatsByUserId")
	public ResponseEntity<ResponseDTO> getAllChatsByUserId(@RequestParam Long userId) {
		String methodName = "getAllChatsByUserId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ChatVO> chatVO = null;
		try {
			chatVO = chatServices.getAllChatByUser(userId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_ID, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "All Chats");
			responseObjectsMap.put("chatVO", chatVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Unable to Get Chats Information", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("/getAllChatsByAdminAndUserId")
	public ResponseEntity<ResponseDTO> getAllChatsByAdminAndUserId(@RequestParam Long userId,@RequestParam Long chatToUser) {
		String methodName = "getAllChatsByAdminAndUserId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ChatVO> chatVO = null;
		try {
			chatVO = chatServices.getAllChatByAdmin(userId,chatToUser);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_ID, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "All Chats");
			responseObjectsMap.put("chatVO", chatVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Unable to Get Chats Information", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/createChat")
	public ResponseEntity<ResponseDTO> createChat(@RequestBody ChatDTO chatDTO) {
		String methodName = "createChat()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		ChatVO chatVO = null;
		try {
			chatVO = chatServices.createChatVO(chatDTO);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error("Unable To Chat", methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Chat Successfully Created");
			responseObjectsMap.put("chatVO", chatVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Unable To Create Chat", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
}