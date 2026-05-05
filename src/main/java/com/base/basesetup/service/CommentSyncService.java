package com.base.basesetup.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.base.basesetup.entity.CommentsVO;

@Service
public class CommentSyncService {

	@Autowired
	private RestTemplate restTemplate;

//	@Async("taskExecutor")
//	public void sendToServerA(CommentsVO vo) {
//
//		try {
//			System.out.println("🚀 Sending B → A for ID: " + vo.getId());
//
//			Map<String, Object> body = new HashMap<>();
//
//			body.put("comments", vo.getComment());
////	        body.put("userName", vo.getCommentName());
//			body.put("sourceTicketId", vo.getTicketId());
//			body.put("sourceId", vo.getId());
//			body.put("sourceUserName", vo.getCommentName());
//			body.put("sourceOrgId", vo.getOrgId());
//			body.put("ticketId", vo.getTicketId());
//
////	        String url = "http://localhost:8021/api/ticketcontroller/createComments";
//			String url = "http://139.5.190.203:8021/api/ticketcontroller/createComments";
//
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.APPLICATION_JSON);
//
//			HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
//
//			System.out.println("➡️ B → A Payload: " + body);
//
//			ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
//
//			System.out.println("✅ B → A Response: " + response.getBody());
//
//		} catch (Exception e) {
//			System.out.println("❌ ERROR B → A");
//			e.printStackTrace();
//		}
//	}

	@Async("taskExecutor")
	public void sendCommentsToMultipleServers(CommentsVO vo) {

		try {
			System.out.println("🚀 Sending B → Multiple Servers for ID: " + vo.getId());

			RestTemplate restTemplate = new RestTemplate();

			// 🔥 SAME BODY FORMAT (unchanged)
			Map<String, Object> body = new HashMap<>();
			body.put("comments", vo.getComment());
			body.put("sourceTicketId", vo.getTicketId());
			body.put("sourceId", vo.getId());
			body.put("sourceUserName", vo.getCommentName());
			body.put("sourceOrgId", vo.getOrgId());
			body.put("ticketId", vo.getTicketId());

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

			List<String> urls = Arrays.asList("http://139.5.190.203:8021/api/ticketcontroller/createComments",
					"http://139.5.190.73:8033/api/ticketcontroller/createComments","http://139.5.190.203:9001/api/ticketcontroller/createComments");

			for (String url : urls) {
				try {
					System.out.println("➡️ Sending to: " + url + " Payload: " + body);

					ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

					System.out.println("✅ Success: " + url + " Response: " + response.getBody());

				} catch (Exception e) {
					System.err.println("❌ Failed: " + url + " Error: " + e.getMessage());
				}
			}

		} catch (Exception e) {
			System.out.println("❌ ERROR sending to multiple servers");
			e.printStackTrace();
		}
	}

	@Async("taskExecutor")
	public void updateToServerA(CommentsVO vo) {

		try {
			Map<String, Object> body = new HashMap<>();

			body.put("comments", vo.getComment());
			body.put("userName", vo.getCommentName());
			body.put("ticketId", vo.getTicketId());

			// 🔥 IMPORTANT LINK
			body.put("sourceId", vo.getId());

			body.put("sourceUserName", vo.getSourceUserName());
			body.put("orgId", vo.getSourceOrgId());

//			String url = "http://localhost:8021/api/ticketcontroller/updateComments";

//			String url = "http://139.5.190.203:8021/api/ticketcontroller/updateComments";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

			List<String> urls = Arrays.asList("http://139.5.190.203:8021/api/ticketcontroller/updateComments",
					"http://139.5.190.73:8033/api/ticketcontroller/updateComments","http://139.5.190.203:9001/api/ticketcontroller/updateComments");

			for (String url : urls) {
				try {
					System.out.println("➡️ UPDATE to: " + url);
					System.out.println("📦 Payload: " + body);

					restTemplate.exchange(url, HttpMethod.PUT, request, String.class);

					System.out.println("✅ Updated: " + url);

				} catch (Exception e) {
					System.err.println("❌ Update Failed: " + url + " | " + e.getMessage());
				}
			}

		} catch (Exception e) {
			System.out.println("❌ UPDATE Sync Error");
			e.printStackTrace();
		}
	}

//	@Async("taskExecutor")
//	public void deleteInServerA(Long sourceId) {
//
//		try {
//
//			String url = "http://139.5.190.203:8021/api/ticketcontroller/deleteComments?sourceId=" + sourceId;
//
//			System.out.println("📤 B → A DELETE URL: " + url);
//
//			restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
//
//			System.out.println("✅ B → A delete synced");
//
//		} catch (Exception e) {
//			System.err.println("❌ Error calling A");
//			e.printStackTrace();
//		}
//	}

	@Async("taskExecutor")
	public void deleteCommentsInMultipleServers(Long sourceId) {

		try {
			System.out.println("🚀 DELETE Sync Start SourceId: " + sourceId);

			RestTemplate restTemplate = new RestTemplate();

			// ✅ ONLY sourceId (as per your requirement)
			Map<String, Object> body = new HashMap<>();
			body.put("sourceId", sourceId);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

			// ✅ MULTIPLE SERVERS
			List<String> urls = Arrays.asList("http://139.5.190.203:8021/api/ticketcontroller/deleteComments",
					"http://139.5.190.73:8033/api/ticketcontroller/deleteComments","http://139.5.190.203:9001/api/ticketcontroller/deleteComments");

			for (String url : urls) {
				try {
					System.out.println("➡️ DELETE to: " + url);
					System.out.println("📦 Payload: " + body);

					restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);

					System.out.println("✅ Deleted in: " + url);

				} catch (Exception e) {
					System.err.println("❌ Delete Failed: " + url + " | " + e.getMessage());
				}
			}

		} catch (Exception e) {
			System.out.println("❌ DELETE Sync Error");
			e.printStackTrace();
		}
	}
}
