package com.base.basesetup.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.base.basesetup.dto.AssignTicketDTO;
import com.base.basesetup.dto.CreateTicketDTO;
import com.base.basesetup.entity.TicketVO;

@Service

public interface TicketService {

	// Create Ticket from User
	
	TicketVO createTicket(CreateTicketDTO createTicketDTO);
	
	List<TicketVO> getAllTicket();
	
	TicketVO assignTicket(AssignTicketDTO assignTicketDTO);
}
