package com.base.basesetup.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

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
	public List<TicketVO> getAllTicketByAssignedTo(String empCode,String userType) {
		System.out.println(userType);
		List<TicketVO>ticketVOs= new ArrayList<>();
		if("Admin".equals(userType))
		{
			ticketVOs= ticketRepo.findAll();
			
		}
		else {
			ticketVOs= ticketRepo.getAllTicketByAssignedTo(empCode);
		}
		
		return ticketVOs;
		
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
		ticketVO.setAssignedToEmp(assignTicketDTO.getAssignedToEmployee());
		Date currentDate=new Date();
		ticketVO.setAssignedDate(currentDate);
		ticketVO.setModifiedBy(assignTicketDTO.getModifiedBy());
		return ticketRepo.save(ticketVO);
	}

	@Override
	public List<TicketVO> getNotificationToEmployee(String empcode) {
		return ticketRepo.findNewTicketNotification(empcode);
	}

	@Override
	public TicketVO changeMflag(Long id) {
		TicketVO ticketVO=ticketRepo.findById(id).get();
		ticketVO.setMflag(true);
		return ticketRepo.save(ticketVO);
	}
	
	@Transactional
    public void updateMflagForAssignedTo(String empCode) {
		ticketRepo.updateMflagByAssignedTo(empCode);
    }

	

	
	
}
