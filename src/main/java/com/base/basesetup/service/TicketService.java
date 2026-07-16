package com.base.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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

	Map<String, Object> assignTicket(AssignTicketDTO assignTicketDTO);

	TicketVO changeTicketStatus(ChangeTicketStatusDTO changeTicketStatusDTO);

	TicketVO saveTicketIssueImage(MultipartFile file, Long id) throws IOException;

	TicketVO getTicketById(Long id);

	List<TicketVO> getNotificationToEmployee(String empcode);

	TicketVO changeMflag(Long id);

	void updateMflagForAssignedTo(String empCode);

//	comments
	List<CommentsVO> getAllComments();

	CommentsVO getCommentsById(Long id);

//	CommentsVO creatComments(CommentDTO commentDTO);

	CommentsVO updateComments(CommentDTO commentDTO);

	TicketCommentImageVO saveTicketCommentImage(MultipartFile file, Long commentId) throws IOException;

	List<TicketCommentImageVO> getAllCommentImageByCommentId(Long commentId);

	List<CommentsVO> getCommentsByTicketId(Long ticketId);

	List<Object[]> getTicketStatusByClient(String customer);

	Set<Object[]> getEmployeeTicketStatusCount();

	List<Map<String, Object>> getTicketStatusCount();

	String uploadTicketBySourceId(MultipartFile file, Long sourceId) throws IOException;

	Map<String, Object> createComments(CommentDTO commentDTO);

	List<CommentsVO> getAllCommentsAnotherServer(Long ticketId);

	List<CommentsVO> getAllCommentsMyServer(Long ticketId);

	void deleteComments(Long id, Long sourceId);

	ResponseEntity<byte[]> viewTicketImage(HttpServletRequest request) throws IOException;

	List<TicketVO> getTicketReports(String application, String fromDate, String toDate);

	List<Map<String, Object>> getApplicationDetails();

	List<Map<String, Object>> getTicketPriorityStatusCount(String assignedTo);

	List<TicketVO> getRecentTicket(String application);

	List<Map<String, Object>> getRecentTopAssign(String application);

	TicketVO assignedPriority(Long ticketId, String priority);

}
