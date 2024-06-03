package com.base.basesetup.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.base.basesetup.dto.ChangeTicketStatusDTO;
import com.base.basesetup.dto.CommentDTO;
import com.base.basesetup.dto.CreateTicketDTO;
import com.base.basesetup.dto.ResponseDTO;
import com.base.basesetup.entity.CommentsVO;
import com.base.basesetup.entity.TicketCommentImageVO;
import com.base.basesetup.entity.TicketVO;
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
			responseDTO = createServiceResponseError(responseObjectsMap, "Unable to Get Ticket Information", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllTicketByAssignedTo")
	public ResponseEntity<ResponseDTO> getAllTicketByAssignedTo(@RequestParam(required = false) String empCode,
			@RequestParam String userType) {
		String methodName = "getAllTicketByAssignedTo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<TicketVO> ticketVO = null;
		try {
			ticketVO = ticketService.getAllTicketByAssignedTo(empCode, userType);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_ID, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "All Tickets");
			responseObjectsMap.put("ticketVO", ticketVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Unable to Get Ticket Information", errorMsg);
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
			LOGGER.error("Unable To Create New Ticket", methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Ticket Successfully Created");
			responseObjectsMap.put("ticketVO", ticketVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Unable To Create Ticket", errorMsg);
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
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PutMapping("/ChangeTicketStatus")
	public ResponseEntity<ResponseDTO> ChangeTicketStatus(@RequestBody ChangeTicketStatusDTO changeTicketStatusDTO ) {
		String methodName = "ChangeTicketStatus()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			TicketVO ticketVO = ticketService.changeTicketStatus(changeTicketStatusDTO);
			if (ticketVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Ticket Assign successfully");
				responseObjectsMap.put("ticketAssign", ticketVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Ticket not found for ID: " + changeTicketStatusDTO.getId();
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
	

	@PutMapping("/changeMflag")
	public ResponseEntity<ResponseDTO> changeMflag(@RequestParam Long id) {
		String methodName = "changeMflag()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			TicketVO ticketVO = ticketService.changeMflag(id);
			if (ticketVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Ticket Assign successfully");
				responseObjectsMap.put("ticketAssign", ticketVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Ticket not found for ID: " + id;
				responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error("Ticket Assign Failed", methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/changeMflagforAllTicket")
	public ResponseEntity<ResponseDTO> changeMflagforAllTicket(@RequestParam String empCode) {
		String methodName = "changeMflagforAllTicket()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			ticketService.updateMflagForAssignedTo(empCode);

		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error("Ticket Assign Failed", methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/upload")
	public ResponseEntity<ResponseDTO> uploadTicketIssue(@RequestParam("file") MultipartFile file,
			@RequestParam Long id) {
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
			LOGGER.error("Unable To Create New Ticket", methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Ticket Successfully Created");
			responseObjectsMap.put("ticketVO", ticketVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Unable To Create Ticket", errorMsg);
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
			responseDTO = createServiceResponseError(responseObjectsMap, "Ticket  not found", errorMsg);
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

	@GetMapping("/getAllTicketNotification")
	public ResponseEntity<ResponseDTO> getAllTicketNotification(@RequestParam String empCode) {
		String methodName = "getAllTicketNotification()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<TicketVO> ticketVO = null;
		try {
			ticketVO = ticketService.getNotificationToEmployee(empCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_ID, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "All Tickets");
			responseObjectsMap.put("ticketVO", ticketVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Unable to Get Ticket Information", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getTicketStatusByClient")
	public ResponseEntity<ResponseDTO> getTicketStatusByClient(@RequestParam String customer) {
		String methodName = "getTicketStatusByClient()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Object[]> ticketVO = new ArrayList<>();
		try {
			ticketVO = ticketService.getTicketStatusByClient(customer);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_ID, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			List<Map<String, Object>>getTicketDetails=getTicketDetailsByClient(ticketVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "All Tickets");
			responseObjectsMap.put("ticketStatusDetails", getTicketDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Unable to Get Ticket Information", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

private List<Map<String, Object>> getTicketDetailsByClient(List<Object[]> ticketVO) {
		List<Map<String, Object>>getTicketDetails= new ArrayList<>();
		for(Object[] tick:ticketVO) {
			Map<String, Object> tickdetails=new HashMap<>();
			tickdetails.put("customer", tick[0] != null ? tick[0].toString() : "");
			tickdetails.put("completed", tick[1] != null ? Integer.parseInt(tick[1].toString()) : 0);
			tickdetails.put("inprogress", tick[2] != null ? Integer.parseInt(tick[2].toString()) : 0);
			tickdetails.put("yetToAssign", tick[3] != null ? Integer.parseInt(tick[3].toString()) : 0);
			tickdetails.put("total", tick[4] != null ? Integer.parseInt(tick[4].toString()) : 0);
			getTicketDetails.add(tickdetails);
		}
		return getTicketDetails;
	}

	//    Comments
	@DeleteMapping("/deleteCommentsById/{id}")
	public ResponseEntity<?> deleteComment(@PathVariable Long id) {
		return ticketService.deleteComments(id);
	}

	@PutMapping("/updateComments")
	public CommentsVO updateComment(@RequestBody CommentDTO commentsVO) {
		return ticketService.updateComments(commentsVO);
	}

	@PostMapping("/createComments")
	public CommentsVO createComment(@RequestBody CommentDTO commentsVO) {
		return ticketService.creatComments(commentsVO);
	}

	@GetMapping("/getCommentsById/{id}")
	public CommentsVO getCommentsById(@PathVariable Long id) {
		return ticketService.getCommentsById(id);
	}

	@GetMapping("/getAllComments")
	public List<CommentsVO> getAllComments() {
		return ticketService.getAllComments();
	}
	
	@GetMapping("/getCommentsByTicketId")
	public List<CommentsVO> getCommentsByTicketId(@RequestParam Long ticketId){
		return ticketService.getCommentsByTicketId(ticketId);
	}
	
	@GetMapping("/getAllCommentImagesByCommentId")
	public ResponseEntity<ResponseDTO> getAllCommentImagesByCommentId(@RequestParam Long commentId) {
		String methodName = "getAllCommentImagesByCommentId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<TicketCommentImageVO> commentImage = null;
		try {
			commentImage = ticketService.getAllCommentImageByCommentId(commentId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_ID, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Comment Image Tickets");
			responseObjectsMap.put("commentImageVO", commentImage);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Unable to Get Comment Image Information", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PostMapping("/uploadCommentImage")
	public ResponseEntity<ResponseDTO> uploadCommentImage(@RequestParam("file") MultipartFile file,
			@RequestParam Long commentId) {
		String methodName = "uploadCommentImage()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		TicketCommentImageVO ticketCommentImageVO = null;
		try {
			ticketCommentImageVO = ticketService.saveTicketCommentImage(file, commentId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error("Unable To Create New Ticket", methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Comment Image Successfully Saved");
			responseObjectsMap.put("ticketCommentImageVO", ticketCommentImageVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Unable To Save Comment Image", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
}
