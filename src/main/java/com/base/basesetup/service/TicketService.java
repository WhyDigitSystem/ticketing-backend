package com.base.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.base.basesetup.dto.AssignTicketDTO;
import com.base.basesetup.dto.ChangeTicketStatusDTO;
import com.base.basesetup.dto.CommentDTO;
import com.base.basesetup.dto.CreateTicketDTO;
import com.base.basesetup.entity.CommentsVO;
import com.base.basesetup.entity.TicketCommentImageVO;
import com.base.basesetup.entity.TicketVO;

@Service

public interface TicketService {

	// Create Ticket from User

	TicketVO createTicket(CreateTicketDTO createTicketDTO);

	List<TicketVO> getAllTicket();

	List<TicketVO> getAllTicketByAssignedTo(String empCode, String userType);

	TicketVO assignTicket(AssignTicketDTO assignTicketDTO);
	
	TicketVO changeTicketStatus(ChangeTicketStatusDTO changeTicketStatusDTO);

	TicketVO saveTicketIssueImage(MultipartFile file, Long id) throws IOException;

	TicketVO getTicketById(Long id);

	List<TicketVO> getNotificationToEmployee(String empcode);

	TicketVO changeMflag(Long id);

	void updateMflagForAssignedTo(String empCode);

//	comments
	List<CommentsVO> getAllComments();

	CommentsVO getCommentsById(Long id);

	CommentsVO creatComments(CommentDTO commentDTO);

	CommentsVO updateComments(CommentDTO commentDTO);
	
	TicketCommentImageVO saveTicketCommentImage(MultipartFile file,Long commentId) throws IOException;
	
	List<TicketCommentImageVO> getAllCommentImageByCommentId(Long commentId);

	ResponseEntity<?> deleteComments(Long id);
	
	List<CommentsVO> getCommentsByTicketId(Long ticketId);
	
	List<Object[]>getTicketStatusByClient(String customer);
	
	Set<Object[]>getEmployeeTicketStatusCount();


//	TicketVO saveTicketIssueImage(MultipartFile file, Long id);

}
