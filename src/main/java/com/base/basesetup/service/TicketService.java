package com.base.basesetup.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.base.basesetup.dto.AssignTicketDTO;
import com.base.basesetup.dto.CreateTicketDTO;
import com.base.basesetup.entity.TicketVO;

@Service

public interface TicketService {

	// Create Ticket from User
	
	TicketVO createTicket(CreateTicketDTO createTicketDTO);
	
	List<TicketVO> getAllTicket();
	
	List<TicketVO> getAllTicketByAssignedTo(String empCode,String userType);
	
	TicketVO assignTicket(AssignTicketDTO assignTicketDTO);

	TicketVO saveTicketIssueImage(MultipartFile file, Long id) throws IOException;

	TicketVO getTicketById(Long id);
	
	List<TicketVO>getNotificationToEmployee(String empcode);
	
	TicketVO changeMflag(Long id);

	void updateMflagForAssignedTo(String empCode);

//	TicketVO saveTicketIssueImage(MultipartFile file, Long id);
}
