package com.base.basesetup.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.base.basesetup.entity.TicketVO;

@Service
public class AsyncEmailService {

	@Autowired
	private EmailService emailService;

	@Async
	public void sendTicketAssignedMail(String from, String to, String subject, String htmlContent) {
		try {
			emailService.sendHtmlEmail(from, to, subject, htmlContent);
		} catch (Exception e) {
			System.err.println("❌ Async Mail failed: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Async
	public void updateOtherServersAsync(TicketVO ticket) {

		RestTemplate restTemplate = new RestTemplate();

		List<String> urls = Arrays.asList("http://139.5.190.203:8021/api/ticketcontroller/updateTicketFromRemote",
				"http://139.5.190.203:9001/api/ticketcontroller/updateTicketFromRemote",
				"http://139.5.190.244:8011/api/ticketcontroller/updateTicketFromRemote",
				"http://139.5.190.73:8053/api/ticketcontroller/updateTicketFromRemote",
				"http://139.5.190.73:8047/api/ticketcontroller/updateTicketFromRemote",
				"http://139.5.190.73:8051/api/ticketcontroller/updateTicketFromRemote",
				"http://139.5.190.203:8033/api/ticketcontroller/updateTicketFromRemote","http://139.5.190.203:8053/api/ticketcontroller/updateTicketFromRemote");

		for (String url : urls) {
			try {
				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
						.queryParam("orgId", ticket.getSourceOrgId()).queryParam("id", ticket.getSourceId())
						.queryParam("email", ticket.getSourceEmail()).queryParam("status", ticket.getStatus())
						.queryParam("empCode", ticket.getModifiedBy())
						.queryParam("ticketStatus", ticket.getTicketStatus());

				restTemplate.put(builder.toUriString(), null);

				System.out.println("✅ Async Updated in: " + url);

			} catch (Exception e) {
				System.err.println("❌ Async Failed in: " + url + " error: " + e.getMessage());
			}
		}
	}

	// ✅ ASYNC EMAIL
	@Async
	public void sendStatusUpdateMail(TicketVO ticket, String from, String adminEmail, String html) {

		String subject = ticket.getDescription() + " - Ticket Status Updated";

		try {
			// Send to user
			if (ticket.getSourceEmail() != null && !ticket.getSourceEmail().isEmpty()) {
				emailService.sendHtmlEmail(from, ticket.getSourceEmail(), subject, html);
			}

			// Send to admin
			if (adminEmail != null && !adminEmail.isEmpty()) {
				emailService.sendHtmlEmail(from, adminEmail, subject, html);
			} else {
				System.err.println("❌ Admin email is null");
			}

		} catch (Exception e) {
			System.err.println("❌ Async Mail failed: " + e.getMessage());
		}
	}
}