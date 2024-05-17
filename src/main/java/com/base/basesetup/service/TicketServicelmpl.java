package com.base.basesetup.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.basesetup.dto.AssignTicketDTO;
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

	@Override
	public TicketVO assignTicket(AssignTicketDTO assignTicketDTO) {
		
		TicketVO ticketVO=ticketRepo.findById(assignTicketDTO.getId()).get();
		ticketVO.setStatus(assignTicketDTO.getStatus());
		ticketVO.setAssignedTo(assignTicketDTO.getAssignedTo());
		Date currentDate=new Date();
		ticketVO.setAssignedDate(currentDate);
		ticketVO.setModifiedBy(assignTicketDTO.getModifiedBy());
		return ticketRepo.save(ticketVO);
	}
	
	

}
