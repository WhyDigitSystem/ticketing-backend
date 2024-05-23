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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile; 

import com.base.basesetup.common.CommonConstant;
import com.base.basesetup.common.UserConstants;
import com.base.basesetup.dto.AssignTicketDTO;
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
	
	@PutMapping("/assignTicket")
	public ResponseEntity<ResponseDTO> assignTicket(@RequestBody AssignTicketDTO assignTicketDTO) {
		String methodName = "assignTicket()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			TicketVO ticketVO = ticketService.assignTicket(assignTicketDTO);
			if (ticketVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Ticket Assign successfully");
				responseObjectsMap.put("ticketAssign", ticketVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Ticket not found for ID: " + assignTicketDTO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error("Ticket Assign Failed", methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PostMapping("/upload")
	public ResponseEntity<ResponseDTO> uploadTicketIssue(@RequestParam("file") MultipartFile file,@RequestParam Long id) {
		String methodName = "uploadTicketIssue()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		TicketVO ticketVO = null;
		try {
			ticketVO = ticketService.saveTicketIssueImage(file, id);
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

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getImage(@PathVariable Long id) {
		String methodName = "getImage()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		TicketVO ticketVO = null;
		try {
			ticketVO = ticketService.getTicketById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Ticket  found by ID");
			responseObjectsMap.put("ticketVO", ticketVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Ticket  not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap,  "Ticket  not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
    
//    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
//        Optional<Image> imageOptional = imageService.getImage(id);
//        if (imageOptional.isPresent()) {
//            Image image = imageOptional.get();
//            return ResponseEntity.ok()
//                    .header( "attachment; filename=\"" + image.getName()+ "\"")
//                    .body(image.getData());
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//    }

	
}
