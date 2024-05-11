package com.base.basesetup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base.basesetup.common.CommonConstant;
import com.base.basesetup.common.UserConstants;
import com.base.basesetup.dto.CreateTicketDTO;
import com.base.basesetup.dto.ResponseDTO;
import com.base.basesetup.entity.TicketVO;
import com.base.basesetup.entity.UserVO;
import com.base.basesetup.service.TicketService;

@CrossOrigin
@RequestMapping("/api/ticket")


@RestController
public class TicketController extends BaseController {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(TicketController.class);

	@Autowired
	TicketService ticketService;
	
	
	@GetMapping("/getAllTicket")
	public ResponseEntity<ResponseDTO> getAllTicket() {
		String methodName = "getAllTicket()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<TicketVO> ticketVO = null;
		try {
			ticketVO = ticketService.getAllTicket();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_ID, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "All Tickets");
			responseObjectsMap.put("ticketVO", ticketVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Unable to Get Ticket Information", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	
	@PostMapping("/createticket")
	public ResponseEntity<ResponseDTO> createticket(@RequestBody CreateTicketDTO createTicketDTO) {
		String methodName = "createticket()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		TicketVO ticketVO = null;
		try {
			ticketVO = ticketService.createTicket(createTicketDTO);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error("Unable To Create New Ticket", methodName, 
					errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Ticket Successfully Created");
			responseObjectsMap.put("ticketVO", ticketVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Unable To Create Ticket",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	
}
