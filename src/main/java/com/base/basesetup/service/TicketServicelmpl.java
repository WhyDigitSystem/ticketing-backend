package com.base.basesetup.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.base.basesetup.dto.AssignTicketDTO;
import com.base.basesetup.dto.ChangeTicketStatusDTO;
import com.base.basesetup.dto.CommentDTO;
import com.base.basesetup.dto.CreateTicketDTO;
import com.base.basesetup.entity.CommentsVO;
import com.base.basesetup.entity.TicketCommentImageVO;
import com.base.basesetup.entity.TicketVO;
import com.base.basesetup.repo.CommentsRepo;
import com.base.basesetup.repo.TicketCommentImageRepo;
import com.base.basesetup.repo.TicketRepo;

@Service
public class TicketServicelmpl implements TicketService {

	@Autowired
	TicketRepo ticketRepo;

	@Autowired
	CommentsRepo commentsRepo;
	
	@Autowired
	TicketCommentImageRepo ticketCommentImageRepo;

	@Override
	public TicketVO createTicket(CreateTicketDTO createTicketDTO) {

		TicketVO tvo = new TicketVO();
		tvo.setTitle(createTicketDTO.getTitle());
		tvo.setDescription(createTicketDTO.getDescription());
		tvo.setClient(createTicketDTO.getClient());
		tvo.setPriority(createTicketDTO.getPriority());
		tvo.setModifiedBy(createTicketDTO.getModifiedBy());
		tvo.setCreatedBy(createTicketDTO.getCreatedBy());
		tvo.setEmail(createTicketDTO.getEmail());
		tvo.setStatus("Yet To Assign");

		return ticketRepo.save(tvo);
	}

	@Override
	public TicketVO saveTicketIssueImage(MultipartFile file, Long id) throws IOException {
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
	public List<TicketVO> getAllTicketByAssignedTo(String empCode, String userType) {
		System.out.println(userType);
		List<TicketVO> ticketVOs = new ArrayList<>();
		if ("Admin".equals(userType)) {
			ticketVOs = ticketRepo.findAll();

		} 
		else if ("Employee".equals(userType)){
			ticketVOs= ticketRepo.getAllTicketByAssignedTo(empCode);
		}
		else
		{
			ticketVOs= ticketRepo.getAllTicketByClient(empCode);
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

		TicketVO ticketVO = ticketRepo.findById(assignTicketDTO.getId()).get();
		ticketVO.setStatus("Inprogress");
		ticketVO.setAssignedTo(assignTicketDTO.getAssignedTo());
		ticketVO.setAssignedToEmp(assignTicketDTO.getAssignedToEmployee());
		Date currentDate = new Date();
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
		TicketVO ticketVO = ticketRepo.findById(id).get();
		ticketVO.setMflag(true);
		return ticketRepo.save(ticketVO);
	}

	@Transactional
	public void updateMflagForAssignedTo(String empCode) {
		ticketRepo.updateMflagByAssignedTo(empCode);
	}



	@Override
	public List <CommentsVO> getAllComments() {
		return commentsRepo.findAll();
	}

	@Override
	public CommentsVO getCommentsById(Long id) {
		return commentsRepo.findById(id).orElse(null);
	}

	@Override
	public CommentsVO creatComments(CommentDTO commentDTO) {
		CommentsVO commentsVO = new CommentsVO();
        commentsVO.setComment(commentDTO.getComment());
        commentsVO.setCommentName(commentDTO.getCommentName());
        commentsVO.setTicketId(commentDTO.getTicketId());
		return commentsRepo.save(commentsVO);
	}

	@Override
	public CommentsVO updateComments(CommentDTO commentDTO) {
		if (commentsRepo.existsById(commentDTO.getId())) {
			CommentsVO commentsVO =commentsRepo.findById(commentDTO.getId()).get();
			commentsVO.setComment(commentDTO.getComment());
	        commentsVO.setCommentName(commentDTO.getCommentName());
	        commentsVO.setTicketId(commentDTO.getTicketId());
			return commentsRepo.save(commentsVO);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found with ID " + commentDTO.getId());
		}
	}

	@Override
	public ResponseEntity<?> deleteComments(Long id) {
		
			commentsRepo.deleteById(id);
			return ResponseEntity.ok().body("Comment with ID " + id + " has been deleted.");
		
	}
	@Override
	public TicketVO changeTicketStatus(ChangeTicketStatusDTO changeTicketStatusDTO) {
	
		TicketVO ticketVO=ticketRepo.findById(changeTicketStatusDTO.getId()).get();
		ticketVO.setStatus(changeTicketStatusDTO.getStatus());
		ticketVO.setModifiedBy(changeTicketStatusDTO.getEmpCode());
		ticketVO.setCompletedBy(changeTicketStatusDTO.getEmpCode());
		Date currentDate=new Date();
		ticketVO.setCompletedOn(currentDate);
		return ticketRepo.save(ticketVO);
	}

	@Override
	public List<CommentsVO> getCommentsByTicketId(Long ticketId) {
		return commentsRepo.findCommentsByTicketId(ticketId);
	}

	@Override
	public List<Object[]> getTicketStatusByClient(String customer) {
		return ticketRepo.getTicketStatusByClient(customer);
	}

	@Override
	public TicketCommentImageVO saveTicketCommentImage(MultipartFile file,Long commentId) throws IOException {
		CommentsVO commentsVO=commentsRepo.findById(commentId).get();
		TicketCommentImageVO ticketCommentImageVO = new TicketCommentImageVO();
		ticketCommentImageVO.setCommentsVO(commentsVO);
		ticketCommentImageVO.setCommentImage(file.getBytes());
		return ticketCommentImageRepo.save(ticketCommentImageVO);
	}

	@Override
	public List<TicketCommentImageVO> getAllCommentImageByCommentId(Long commentId) {
		
		return ticketCommentImageRepo.findCommentImageByComentId(commentId);
	}

	@Override
	public Set<Object[]> getEmployeeTicketStatusCount() {
		
		return ticketRepo.getEmployeeTicketStatusCounts();
	}

	@Override
	public List<Map<String, Object>> getTicketStatusCount() {
		
		Set<Object[]>getTicketStatusCountDetails=ticketRepo.getStatusCountDetails();
		return getTicketCount(getTicketStatusCountDetails);
	}

	private List<Map<String, Object>> getTicketCount(Set<Object[]> getTicketStatusCountDetails) {
		List<Map<String,Object>> tickets= new ArrayList<>();
		for(Object[] tick:getTicketStatusCountDetails)
		{
			Map<String,Object>t= new HashMap<>();
			t.put("status", tick[0] != null ? tick[0].toString() : "");
			t.put("ticketCount", tick[1] != null ? Integer.parseInt(tick[1].toString()) : 0);
			tickets.add(t);
		}
		return tickets;
	}

	@Override
	public List<Map<String, Object>> getTicketPriorityStatusCount() {
		
		Set<Object[]>getTicketPriorityStatusCountDetails=ticketRepo.getPriorityStatusCountDetails();
		return getPriorityTicketCount(getTicketPriorityStatusCountDetails);
	}

	private List<Map<String, Object>> getPriorityTicketCount(Set<Object[]> getTicketPriorityStatusCountDetails) {
		List<Map<String,Object>> tickets= new ArrayList<>();
		for(Object[] tick:getTicketPriorityStatusCountDetails)
		{
			Map<String,Object>t= new HashMap<>();
			t.put("high", tick[0] != null ? Integer.parseInt(tick[0].toString()) : 0);
			t.put("normal", tick[1] != null ? Integer.parseInt(tick[1].toString()) : 0);
			t.put("medium", tick[2] != null ? Integer.parseInt(tick[2].toString()) : 0);
			t.put("total", tick[3] != null ? Integer.parseInt(tick[3].toString()) : 0);
			tickets.add(t);
		}
		return tickets;
	}
}
