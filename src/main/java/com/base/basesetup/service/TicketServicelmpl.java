package com.base.basesetup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.basesetup.dto.CreateTicketDTO;
import com.base.basesetup.entity.TicketVO;
import com.base.basesetup.repo.TicketRepo;

@Service
public class TicketServicelmpl  implements TicketService{
	
	@Autowired
	TicketRepo ticketRepo;

	@Override
	public TicketVO createTicket(CreateTicketDTO createTicketDTO) {
		
		TicketVO tvo = new TicketVO();
		tvo.setTitle(createTicketDTO.getTitle());
		tvo.setDescription(createTicketDTO.getDescription());
		tvo.setClient(createTicketDTO.getClient());
		tvo.setPriority(createTicketDTO.getPriority());
		tvo.setModifiedBy(createTicketDTO.getModifiedBy());
		tvo.setCreatedBy(createTicketDTO.getCreatedBy());
		tvo.setStatus("Yet To Assign");
		
		return ticketRepo.save(tvo);
	}

	@Override
	public List<TicketVO> getAllTicket() {
		// TODO Auto-generated method stub
		
		return ticketRepo.findAll();
	}
	
	

}
