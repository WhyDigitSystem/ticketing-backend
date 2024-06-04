package com.base.basesetup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base.basesetup.common.CommonConstant;
import com.base.basesetup.common.UserConstants;
import com.base.basesetup.dto.CreateTicketDTO;
import com.base.basesetup.dto.InvoiceDTO;
import com.base.basesetup.dto.ResponseDTO;
import com.base.basesetup.entity.InvoiceVO;
import com.base.basesetup.entity.TicketVO;
import com.base.basesetup.service.InvoiceService;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController extends BaseController {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(InvoiceController.class);
	
	@Autowired
	InvoiceService invoiceService;
	
	
	
	@GetMapping("/getAllInvoice")
	public ResponseEntity<ResponseDTO> getAllInvoiceVO() {
		String methodName = "getAllInvoice()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<InvoiceVO> invoiceVO = null;
		try {
			invoiceVO = invoiceService.getAllInvoiceVO();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_ID, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "All Invoices");
			responseObjectsMap.put("invoiceVO", invoiceVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Unable to Get Invoice Information", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("/getInvoiceById")
	public ResponseEntity<ResponseDTO> getInvoiceById(@RequestParam Long id) {
		String methodName = "getInvoiceById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		InvoiceVO invoiceVO = null;
		try {
			invoiceVO = invoiceService.getInvoiceById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_ID, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Invoices"+id);
			responseObjectsMap.put("invoiceVO", invoiceVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Unable to Get Invoice Information", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@PostMapping("/createinvoice")
	public ResponseEntity<ResponseDTO> CreateInvoice(@RequestBody InvoiceDTO invoiceDTO) {
		String methodName = "CreateInvoice()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		InvoiceVO invoiceVO = null;
		try {
			invoiceVO = invoiceService.createInvoiceVO(invoiceDTO);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error("Unable To Generate Invoice", methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Invoice Generated");
			responseObjectsMap.put("invoiceVO", invoiceVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PutMapping("/invoice")
	public ResponseEntity<ResponseDTO> updateInvoice(@RequestBody InvoiceDTO invoiceDTO) {
		String methodName = "updateInvoice()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			InvoiceVO updatedInvoiceVO = invoiceService.updateInvoice(invoiceDTO);
			if (updatedInvoiceVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Invoice updated successfully");
				responseObjectsMap.put("invoiceVO", updatedInvoiceVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Invoice not found for ID: " + invoiceDTO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap,errorMsg,errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@DeleteMapping("deleteInvoiceDetails")
	public ResponseEntity<ResponseDTO> deleteInvoiceDetails(@RequestParam Long id) {
		String methodName = "deleteInvoiceDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			invoiceService.deleteInvoiceDetails(id);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Invoice Details deleted successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Invoice Details deletion failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	

}
