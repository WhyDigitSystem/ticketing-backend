package com.base.basesetup.service;

import java.awt.Image;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
	public TicketVO saveTicketIssueImage(MultipartFile file,Long id) throws IOException {
		TicketVO ticketVO = ticketRepo.findById(id).get();
		ticketVO.setImageData(file.getBytes());
        return ticketRepo.save(ticketVO);
    }

	@Override
	public List<TicketVO> getAllTicket() {
		// TODO Auto-generated method stub
		return ticketRepo.findAll();
	}
	
	@Override
	public TicketVO getTicketById(Long id) {
		// TODO Auto-generated method stub
		return ticketRepo.findById(id).get();
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
