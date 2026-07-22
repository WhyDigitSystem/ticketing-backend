package com.base.basesetup.service;

import java.io.IOException;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

	@Autowired
	EmailService emailService;

	@Value("${app.mail.noreplay}")
	private String noReplayEmail;

	@Value("${app.mail.adminEmail}")
	private String adminEmail;

	@Autowired
	private AsyncEmailService asyncEmailService;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	CommentSyncService commentSyncService;

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
		tvo.setSourceId(createTicketDTO.getSourceId());
		tvo.setSourceOrgId(createTicketDTO.getSourceOrgId());
		tvo.setSourceBranch(createTicketDTO.getSourceBranch());
		tvo.setSourceBranchCode(createTicketDTO.getSourceBranchCode());
		tvo.setCustomer(createTicketDTO.getCustomer());
		tvo.setProjectName(createTicketDTO.getProjectName());
		tvo.setApplication(createTicketDTO.getApplication());
		tvo.setSourceEmail(createTicketDTO.getSourceEmail());
		tvo.setTicketStatus(createTicketDTO.getTicketStatus());
		tvo.setStatus("Yet To Assign");
		return ticketRepo.save(tvo);
	}

	@Override
	public TicketVO saveTicketIssueImage(MultipartFile file, Long id) throws IOException {
		TicketVO ticketVO = ticketRepo.findById(id).get();
		ticketVO.setImageData(file.getBytes());
		return ticketRepo.save(ticketVO);
	}

//	@Override
//	public TicketVO uploadTicketBySourceId(MultipartFile file, Long sourceId) throws IOException {
//		TicketVO ticketVO = ticketRepo.findBySourceId(sourceId);
//		ticketVO.setImageData(file.getBytes());
//		return ticketRepo.save(ticketVO);
//	}

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

		} else if ("Employee".equals(userType)) {
			ticketVOs = ticketRepo.getAllTicketByAssignedTo(empCode);
		} else {
			ticketVOs = ticketRepo.getAllTicketByClient(empCode);
		}

		return ticketVOs;

	}

	@Override
	public TicketVO getTicketById(Long id) {
		// TODO Auto-generated method stub
		return ticketRepo.findById(id).get();
	}

//
//	@Value("${app.mail.adminEmail}")
//	private String adminEmail;

//	@Override
//	public Map<String, Object> assignTicket(AssignTicketDTO dto) {
//
//		TicketVO ticket = ticketRepo.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Ticket not found"));
//
//		// Update ticket
//		ticket.setStatus("Inprogress");
//		ticket.setAssignedTo(dto.getAssignedTo());
//		ticket.setAssignedToEmp(dto.getAssignedToEmployee());
//		ticket.setAssignedDate(LocalDate.now());
//		ticket.setModifiedBy(dto.getModifiedBy());
//		ticket.setEmail(dto.getEmail());
//
//		TicketVO savedTicket = ticketRepo.save(ticket);
//
//		boolean mailSent = false;
//		String message;
//
//		try {
//			String subject = "🎫 Ticket Assigned - ID: " + savedTicket.getId();
//
//			String createdOn = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a").format(new Date());
//
//			String htmlContent = loadHtmlTemplate(savedTicket.getId(), subject, savedTicket.getStatus(),
//					savedTicket.getDescription(), savedTicket.getCreatedBy(), savedTicket.getEmail(), createdOn);
//
//			// Send email
//			emailService.sendHtmlEmail(noReplayEmail, savedTicket.getEmail(), subject, htmlContent);
//
//			mailSent = true;
//
//		} catch (Exception e) {
//			System.err.println("❌ Mail sending failed: " + e.getMessage());
//			e.printStackTrace();
//		}
//
//		message = mailSent ? "Ticket assigned successfully and mail sent."
//				: "Ticket assigned successfully but mail failed.";
//
//		Map<String, Object> response = new HashMap<>();
//		response.put("message", message);
//		response.put("ticket", savedTicket);
//
//		return response;
//	}

//	// Load HTML Template
//	public String loadHtmlTemplate(Long ticketId, String subject, String status, String description, String createdBy,
//			String email, String createdOn) {
//
//		try {
//			ClassPathResource resource = new ClassPathResource("template/email_template.html");
//
//			String content = new String(resource.getInputStream().readAllBytes());
//
//			return content.replace("${ticketId}", ticketId.toString()).replace("${subject}", subject)
//					.replace("${status}", status).replace("${description}", description)
//					.replace("${raisedBy}", createdBy).replace("${raisedEmail}", email)
//					.replace("${raisedOn}", createdOn);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "<p>Error loading email template</p>";
//		}
//	}

	@Override
	public Map<String, Object> assignTicket(AssignTicketDTO dto) {

		TicketVO ticket = ticketRepo.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Ticket not found"));

		// Update ticket
		ticket.setStatus("Inprogress");
		ticket.setAssignedTo(dto.getAssignedTo());
		ticket.setAssignedToEmp(dto.getAssignedToEmployee());
		ticket.setAssignedDate(LocalDate.now());
		ticket.setModifiedBy(dto.getModifiedBy());
		ticket.setEmail(dto.getEmail());
		ticket.setAssignPriority(dto.getAssignPriority());
		TicketVO savedTicket = ticketRepo.save(ticket);

		String message;

		try {
			String subject = "🎫 Ticket Assigned - ID: " + savedTicket.getId();

			String createdOn = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a").format(new Date());

			String htmlContent = loadHtmlTemplate(savedTicket.getId(), subject, savedTicket.getStatus(),
					savedTicket.getDescription(), savedTicket.getCreatedBy(), savedTicket.getEmail(), createdOn);

			// ✅ ASYNC CALL (NON-BLOCKING)
			asyncEmailService.sendTicketAssignedMail(noReplayEmail, savedTicket.getEmail(), subject, htmlContent);

			message = "Ticket assigned successfully and mail triggered.";

		} catch (Exception e) {
			System.err.println("❌ Error preparing mail: " + e.getMessage());
			message = "Ticket assigned successfully but mail preparation failed.";
		}

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("ticket", savedTicket);

		return response;
	}

	public String loadHtmlTemplate(Long ticketId, String subject, String status, String description, String createdBy,
			String email, String createdOn) {

		try {
			ClassPathResource resource = new ClassPathResource("template/email_template.html");

			String content = new String(resource.getInputStream().readAllBytes());

			return content.replace("${ticketId}", ticketId.toString()).replace("${subject}", subject)
					.replace("${status}", status).replace("${description}", description)
					.replace("${raisedBy}", createdBy).replace("${raisedEmail}", email)
					.replace("${raisedOn}", createdOn);

		} catch (Exception e) {
			e.printStackTrace();
			return "<p>Error loading email template</p>";
		}
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
	public List<CommentsVO> getAllComments() {
		return commentsRepo.findAll();
	}

	@Override
	public CommentsVO getCommentsById(Long id) {
		return commentsRepo.findById(id).orElse(null);
	}

//	@Override
//	public Map<String,Obj> creatComments(CommentDTO commentDTO) {
//		CommentsVO commentsVO = new CommentsVO();
//		commentsVO.setComment(commentDTO.getComment());
//		commentsVO.setCommentName(commentDTO.getCommentName());
//		commentsVO.setTicketId(commentDTO.getTicketId());
//		return commentsRepo.save(commentsVO);
//	}

//	@Override
//	public CommentsVO updateComments(CommentDTO commentDTO) {
//		if (commentsRepo.existsById(commentDTO.getId())) {
//			CommentsVO commentsVO = commentsRepo.findById(commentDTO.getId()).get();
//			commentsVO.setComment(commentDTO.getComment());
//			commentsVO.setCommentName(commentDTO.getCommentName());
//			commentsVO.setTicketId(commentDTO.getTicketId());
//			return commentsRepo.save(commentsVO);
//		} else {
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found with ID " + commentDTO.getId());
//		}
//	}

//	@Override
//	public TicketVO changeTicketStatus(ChangeTicketStatusDTO changeTicketStatusDTO) {
//
//		TicketVO ticketVO = ticketRepo.findById(changeTicketStatusDTO.getId()).get();
//		ticketVO.setStatus(changeTicketStatusDTO.getStatus());
//		ticketVO.setModifiedBy(changeTicketStatusDTO.getEmpCode());
//		ticketVO.setCompletedBy(changeTicketStatusDTO.getEmpCode());
//		Date currentDate = new Date();
//		ticketVO.setCompletedOn(currentDate);	
//		TicketVO ticketVO1= ticketRepo.save(ticketVO);
//		
//		  String url = "http://localhost:8021/api/ticketcontroller/findByOrgIdAndId";
//		  
//		  String url = "http://localhost:9001/api/ticketServicecontroller/findByOrgIdAndId";
//		  
//		  String url = "http://localhost:8011/api/ticketSeriveImplcontroller/findByOrgIdAndId";
//		
//		String Ticketstatus = ticketVO.getSubject() + " - Ticket Status Updated";
//		
//
//		try {
//			String htmlContent = loadHtmlTemplateUpdateMail(ticketVO.getId(), Ticketstatus, ticketVO.getStatus(),
//					ticketVO.getDescription());
//			emailService.sendHtmlEmail(noReplayEmail, ticketVO.getSourceEmail(), Ticketstatus, htmlContent);
//			mailSent = true;
//
//		} catch (Exception e) {
//			System.err.println("❌ Failed to send mail for ticket ID " + ticketVO.getId() + ": " + e.getMessage());
//			e.printStackTrace();
//		}
//		
//		
//	}

//	@Override
//	public TicketVO changeTicketStatus(ChangeTicketStatusDTO dto) {
//
//		TicketVO ticket = ticketRepo.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Ticket not found"));
//
//		// ✅ Local Update
//		ticket.setStatus(dto.getStatus());
//		ticket.setTicketStatus(dto.getTicketStatus());
//		ticket.setModifiedBy(dto.getEmpCode());
//		ticket.setCompletedBy(dto.getEmpCode());
//		ticket.setCompletedOn(new Date());
//
//		TicketVO savedTicket = ticketRepo.save(ticket);
//
//		// ✅ Call remote servers
//		updateOtherServers(savedTicket);
//
//		// ✅ Send Email
//		sendMail(savedTicket);
//
//		return savedTicket;
//	}
//
//	private void updateOtherServers(TicketVO ticket) {
//
//		RestTemplate restTemplate = new RestTemplate();
//
//		List<String> urls = Arrays.asList("http://139.5.190.203:8021/api/ticketcontroller/updateTicketFromRemote",
//				"http://139.5.190.203:9001/api/ticketcontroller/updateTicketFromRemote",
//				"http://localhost:8011/api/ticketcontroller/updateTicketFromRemote");
//
//		for (String url : urls) {
//			try {
//
//				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
//						.queryParam("orgId", ticket.getSourceOrgId()).queryParam("id", ticket.getSourceId())
//						.queryParam("email", ticket.getSourceEmail()).queryParam("status", ticket.getStatus())
//						.queryParam("empCode", ticket.getModifiedBy())
//						.queryParam("ticketStatus", ticket.getTicketStatus());
//				restTemplate.put(builder.toUriString(), null);
//
//				System.out.println("✅ Updated in: " + url);
//
//			} catch (Exception e) {
//				System.err.println("❌ Failed in: " + url + " error: " + e.getMessage());
//			}
//		}
//	}
//
//
//	private void sendMail(TicketVO ticket) {
//
//		String subject = ticket.getDescription() + " - Ticket Status Updated";
//
//		try {
//			String html = loadHtmlTemplateUpdateMail(ticket.getId(), subject, ticket.getStatus(),
//					ticket.getTicketStatus());
//
//			// ✅ Debug (important)
//			System.out.println("From: " + noReplayEmail);
//			System.out.println("User Email: " + ticket.getSourceEmail());
//			System.out.println("Admin Email: " + adminEmail);
//
//			// ✅ Send to user
//			if (ticket.getSourceEmail() != null && !ticket.getSourceEmail().isEmpty()) {
//				emailService.sendHtmlEmail(noReplayEmail, ticket.getSourceEmail(), subject, html);
//			}
//
//			// ✅ Send to admin (fix issue)
//			if (adminEmail != null && !adminEmail.isEmpty()) {
//				emailService.sendHtmlEmail(noReplayEmail, adminEmail, subject, html);
//			} else {
//				System.err.println("❌ Admin email is null");
//			}
//
//		} catch (Exception e) {
//			System.err.println("❌ Mail failed: " + e.getMessage());
//			e.printStackTrace();
//		}
//	}

	@Override
	public TicketVO changeTicketStatus(ChangeTicketStatusDTO dto) {

		TicketVO ticket = ticketRepo.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Ticket not found"));

		// ✅ Update
		ticket.setStatus(dto.getStatus());
		ticket.setTicketStatus(
			    "Your Ticket Resolved Please Check!\n"
			    + "Thank You"
			);
		ticket.setModifiedBy(dto.getEmpCode());
		ticket.setCompletedBy(dto.getEmpCode());
		ticket.setCompletedOn(new Date());

		TicketVO savedTicket = ticketRepo.save(ticket);

		try {
			String subject = savedTicket.getDescription() + " - Ticket Status Updated";

			String html = loadHtmlTemplateUpdateMail(savedTicket.getId(), subject, savedTicket.getStatus(),
					savedTicket.getTicketStatus());

			// ✅ ASYNC CALLS (NO WAIT)
			asyncEmailService.updateOtherServersAsync(savedTicket);
			asyncEmailService.sendStatusUpdateMail(savedTicket, noReplayEmail, adminEmail, html);

		} catch (Exception e) {
			System.err.println("❌ Error preparing async tasks: " + e.getMessage());
		}

		return savedTicket; // ⚡ instant response
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
	public TicketCommentImageVO saveTicketCommentImage(MultipartFile file, Long commentId) throws IOException {
		CommentsVO commentsVO = commentsRepo.findById(commentId).get();
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

		Set<Object[]> getTicketStatusCountDetails = ticketRepo.getStatusCountDetails();
		return getTicketCount(getTicketStatusCountDetails);
	}

	private List<Map<String, Object>> getTicketCount(Set<Object[]> getTicketStatusCountDetails) {
		List<Map<String, Object>> tickets = new ArrayList<>();
		for (Object[] tick : getTicketStatusCountDetails) {
			Map<String, Object> t = new HashMap<>();
			t.put("status", tick[0] != null ? tick[0].toString() : "");
			t.put("ticketCount", tick[1] != null ? Integer.parseInt(tick[1].toString()) : 0);
			tickets.add(t);
		}
		return tickets;
	}

	@Override
	public List<Map<String, Object>> getTicketPriorityStatusCount(String assignedTo) {

		Set<Object[]> getTicketPriorityStatusCountDetails = ticketRepo.getPriorityStatusCountDetails(assignedTo);
		return getPriorityTicketCount(getTicketPriorityStatusCountDetails);
	}

	private List<Map<String, Object>> getPriorityTicketCount(Set<Object[]> getTicketPriorityStatusCountDetails) {
		List<Map<String, Object>> tickets = new ArrayList<>();
		for (Object[] tick : getTicketPriorityStatusCountDetails) {
			Map<String, Object> t = new HashMap<>();
			t.put("high", tick[0] != null ? Integer.parseInt(tick[0].toString()) : 0);
			t.put("normal", tick[1] != null ? Integer.parseInt(tick[1].toString()) : 0);
			t.put("medium", tick[2] != null ? Integer.parseInt(tick[2].toString()) : 0);
			t.put("total", tick[3] != null ? Integer.parseInt(tick[3].toString()) : 0);
			t.put("highPer", tick[4] != null ? new BigDecimal(tick[4].toString()) : BigDecimal.ZERO);
			t.put("normalPer", tick[5] != null ? new BigDecimal(tick[5].toString()) : BigDecimal.ZERO);
			t.put("mediumPer", tick[6] != null ? new BigDecimal(tick[6].toString()) : BigDecimal.ZERO);
			tickets.add(t);
		}
		return tickets;
	}

	public String loadHtmlTemplateUpdateMail(Long ticketId, String subject, String status, String description) {

		try {
			ClassPathResource resource = new ClassPathResource("template/Updates_mail.html");

			if (!resource.exists()) {
				throw new RuntimeException("Template file NOT found");
			}

			InputStream inputStream = resource.getInputStream();
			String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

			String formattedStatus = status;

			String statusColor = "#10b981"; // default

			String statusUpper = status.toUpperCase();
			if (statusUpper.contains("PENDING"))
				statusColor = "orange";
			else if (statusUpper.contains("REJECTED"))
				statusColor = "red";

			return content.replace("${ticketId}", String.valueOf(ticketId))
					.replace("${subject}", subject != null ? subject : "").replace("${status}", formattedStatus)
					.replace("${statusColor}", statusColor)
					.replace("${description}", description != null ? description : "");

		} catch (Exception e) {
			e.printStackTrace();
			return "<h2 style='color:red;'>Template not loaded</h2>";
		}
	}

	@Override
	public Map<String, Object> createComments(CommentDTO dto) {

		Map<String, Object> response = new HashMap<>();

		try {
			System.out.println("📥 B received SourceId: " + dto.getSourceId());

			CommentsVO vo = new CommentsVO();

			vo.setComment(dto.getComment());
			vo.setCommentName(dto.getCommentName());
			vo.setTicketId(dto.getTicketId());
			vo.setOrgId(dto.getOrgId());
			vo.setSourceId(dto.getSourceId());
			vo.setSourceTicketId(dto.getSourceTicketId());
			vo.setSourceUserName(dto.getSourceUserName());
			vo.setSourceOrgId(dto.getSourceOrgId());
			vo.setApplication(dto.getApplication());

			commentsRepo.save(vo);

			System.out.println("💾 Saved in Server B: " + vo.getId());

			if (dto.getSourceId() == null || dto.getSourceId() == 0) {
				System.out.println("🔁 B → A Triggered");
				commentSyncService.sendCommentsToMultipleServers(vo);
			} else {
				System.out.println("⛔ Skipping B → A (came from A)");
			}

			response.put("status", true);
			response.put("message", "Saved in Server B");
			response.put("commentVO", vo);

		} catch (Exception e) {
			e.printStackTrace();
			response.put("status", false);
			response.put("message", e.getMessage());
		}

		return response;
	}

	@Override
	public List<CommentsVO> getAllCommentsAnotherServer(Long ticketId) {
		return commentsRepo.getAllCommentsAnotherServer(ticketId);

	}

	@Override
	public List<CommentsVO> getAllCommentsMyServer(Long ticketId) {
		return commentsRepo.getAllCommentsMyServer(ticketId);

	}

	@Override
	public CommentsVO updateComments(CommentDTO dto) {

		CommentsVO vo;

		if (dto.getId() != null) {

			vo = commentsRepo.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Not found in B by id"));

			System.out.println("✏️ Updating in B using commentsid");

			vo.setComment(dto.getComment());
			vo.setCommentName(dto.getCommentName());
			vo.setTicketId(dto.getTicketId());

			commentsRepo.save(vo);

			commentSyncService.updateToServerA(vo);
		}

		else if (dto.getSourceId() != null) {

			vo = commentsRepo.findBySourceId(dto.getSourceId())
					.orElseThrow(() -> new RuntimeException("Not found in B by sourceId"));

			System.out.println("✏️ Updating in B using sourceId");

			vo.setComment(dto.getComment());
			vo.setCommentName(dto.getCommentName());
			vo.setTicketId(dto.getTicketId());

			commentsRepo.save(vo);
		}

		else {
			throw new RuntimeException("❌ id and sourceId both NULL");
		}

		return vo;
	}

//	@Override
//	public void deleteComments(Long id, Long sourceId) {
//
//		if (id != null) {
//
//			CommentsVO vo = commentsRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found in B"));
//
//			commentsRepo.delete(vo);
//
//			System.out.println("🗑️ Deleted in Server B (LOCAL)");
//
//			commentSyncService.deleteCommentsInMultipleServers(vo.getId());
//		}
//
//		else if (sourceId != null) {
//
//			CommentsVO vo = commentsRepo.findBySourceId(sourceId)
//					.orElseThrow(() -> new RuntimeException("Not found in B by sourceId"));
//
//			commentsRepo.delete(vo);
//
//			System.out.println("🗑️ Deleted in Server B (SYNC)");
//		}
//
//		else {
//			throw new RuntimeException("❌ id and sourceId both NULL");
//		}
//	}

	@Override
	public void deleteComments(Long id, Long sourceId) {

		// ✅ LOCAL DELETE
		if (id != null) {

			CommentsVO vo = commentsRepo.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));

			Long syncId = (vo.getSourceId() != null && vo.getSourceId() != 0) ? vo.getSourceId() : vo.getId();

			commentsRepo.delete(vo);

			System.out.println("🗑️ LOCAL DELETE SUCCESS");

			// ✅ sync delete
			commentSyncService.deleteCommentsInMultipleServers(syncId);
		}

		// ✅ SYNC DELETE
		else if (sourceId != null) {

			Optional<CommentsVO> optional = commentsRepo.findBySourceId(sourceId);

			if (optional.isPresent()) {

				commentsRepo.delete(optional.get());

				System.out.println("🗑️ SYNC DELETE SUCCESS sourceId: " + sourceId);

			} else {

				System.out.println("⚠️ No Record Found sourceId: " + sourceId);
			}
		}

		else {

			throw new RuntimeException("❌ BOTH NULL");
		}
	}

	@Value("${file.upload-dirs}")
	private String uploadBasePath;

	@Override
	@Transactional
	public String uploadTicketBySourceId(MultipartFile file, Long sourceId) throws IOException {

		TicketVO ticketVO = ticketRepo.findBySourceId(sourceId);

		// CREATE NEW RECORD
		if (ticketVO == null) {

			ticketVO = new TicketVO();

			ticketVO.setSourceId(sourceId);
		}

		// BASE FOLDER
		Path ticketFolder = Paths.get(uploadBasePath, "ticketimages", sourceId.toString());

		createDirectoryTicket(ticketFolder);

		// DELETE OLD FILE
		if (ticketVO.getFilePath() != null && !ticketVO.getFilePath().isEmpty()) {

			deleteFileSafelyTicket(ticketVO.getFilePath());
		}

		// ORIGINAL FILE NAME
		String originalName = file.getOriginalFilename();

		if (originalName == null) {

			originalName = "file";
		}

		// REMOVE SPACES
		originalName = originalName.replaceAll("\\s+", "_");

		// EXTENSION
		String extension = "";

		if (originalName.contains(".")) {

			extension = originalName.substring(originalName.lastIndexOf("."));

			originalName = originalName.substring(0, originalName.lastIndexOf("."));
		}

		// NEW FILE NAME
		String fileName = originalName + "_" + sourceId + extension;

		// FINAL FILE PATH
		Path filePath = ticketFolder.resolve(fileName);

		// SAVE FILE
		try (InputStream inputStream = file.getInputStream()) {

			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		}

		// BASE URL
		String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/ticket/viewTicketImage/")
				.toUriString();

		// RELATIVE PATH
		String relativePath = uploadBasePath.replace("\\", "/");

		relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");

		// PUBLIC URL
		String publicUrl = baseUrl + relativePath;

		// SAVE DB
		ticketVO.setFileName(fileName);

		ticketVO.setFilePath(publicUrl);

		ticketVO.setFileSize(file.getSize());

		ticketVO.setContentType(file.getContentType());

		ticketVO.setUploadOn(LocalDateTime.now());

		ticketRepo.save(ticketVO);

		System.out.println("FILE SAVED : " + filePath.toAbsolutePath());

		System.out.println("PUBLIC URL : " + publicUrl);

		return "Image uploaded successfully";
	}

	private void deleteFileSafelyTicket(String fileUrl) {

		try {

			String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

			String relativePath = fileUrl.replace(baseUrl + "/api/ticket/viewTicketImage/", "");

			Path filePath = Paths.get(uploadBasePath, relativePath);

			if (Files.exists(filePath)) {

				Files.delete(filePath);

				System.out.println("Old file deleted : " + filePath);
			}

		} catch (Exception e) {

			System.err.println("Unable to delete file : " + fileUrl);
		}
	}

	private void createDirectoryTicket(Path path) throws IOException {

		if (!Files.exists(path)) {

			Files.createDirectories(path);
		}
	}

	@Override
	public ResponseEntity<byte[]> viewTicketImage(HttpServletRequest request) throws IOException {

		return serveFileTicket(request, "/api/ticket/viewTicketImage/", uploadBasePath);
	}

	private ResponseEntity<byte[]> serveFileTicket(HttpServletRequest request, String apiPrefix, String uploadBasePath)
			throws IOException {

		String uri = request.getRequestURI();

		// REMOVE API PREFIX
		String relativePath = uri.replace(apiPrefix, "");

		// URL DECODE
		relativePath = URLDecoder.decode(relativePath, StandardCharsets.UTF_8);

		// REMOVE uploads/
		if (relativePath.startsWith("uploads/")) {

			relativePath = relativePath.substring("uploads/".length());
		}

		Path baseDir = Paths.get(uploadBasePath).toAbsolutePath().normalize();

		Path filePath = baseDir.resolve(relativePath).normalize();

		// SECURITY CHECK
		if (!filePath.startsWith(baseDir)) {

			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		// FILE EXISTS
		if (!Files.exists(filePath)) {

			return ResponseEntity.notFound().build();
		}

		String contentType = Files.probeContentType(filePath);

		if (contentType == null) {

			contentType = "application/octet-stream";
		}

		byte[] data = Files.readAllBytes(filePath);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline").body(data);
	}

	@Override
	public List<TicketVO> getTicketReports(String application, String fromDate, String toDate) {

		return ticketRepo.getTicketReports(application, fromDate, toDate);
	}

	@Override
	public List<Map<String, Object>> getApplicationDetails() {

		Set<Object[]> getApplicationDetails = ticketRepo.getApplicationDetails();
		return getApplicationDetails(getApplicationDetails);
	}

	private List<Map<String, Object>> getApplicationDetails(Set<Object[]> getApplicationDetails) {
		List<Map<String, Object>> tickets = new ArrayList<>();
		for (Object[] tick : getApplicationDetails) {
			Map<String, Object> t = new HashMap<>();
			t.put("application", tick[0] != null ? tick[0].toString() : "");
			tickets.add(t);
		}
		return tickets;
	}

	@Override
	public List<TicketVO> getRecentTicket(String application) {

		return ticketRepo.getRecentTicket(application);
	}

	@Override
	public List<Map<String, Object>> getRecentTopAssign(String application) {

		Set<Object[]> getApplicationDetails = ticketRepo.getRecentTopAssign(application);
		return getRecentTopAssign(getApplicationDetails);
	}

	private List<Map<String, Object>> getRecentTopAssign(Set<Object[]> getApplicationDetails) {
		List<Map<String, Object>> tickets = new ArrayList<>();
		for (Object[] tick : getApplicationDetails) {
			Map<String, Object> t = new HashMap<>();
			t.put("application", tick[0] != null ? tick[0].toString() : "");
			t.put("count", tick[1] != null ? tick[1].toString() : "");
			tickets.add(t);
		}
		return tickets;
	}

	@Override
	public TicketVO assignedPriority(Long ticketId, String priority) {

		TicketVO ticket = ticketRepo.getTicketStatusId(ticketId);

		if (ticket == null) {
			throw new RuntimeException("Ticket not found with ID: " + ticketId);
		}

		ticket.setPriority(priority);

		return ticketRepo.save(ticket);
	}

}
