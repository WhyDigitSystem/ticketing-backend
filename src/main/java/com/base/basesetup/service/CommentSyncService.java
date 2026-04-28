package com.base.basesetup.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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

	@Async("taskExecutor")
	public void sendToServerA(CommentsVO vo) {

		try {
			System.out.println("🚀 B → A Sync STARTED for ID: " + vo.getId());

			Map<String, Object> body = new HashMap<>();
			body.put("comments", vo.getComment());
			body.put("userName", vo.getCommentName());
			body.put("ticketId", vo.getTicketId());

			// جلوگیری از loop
			body.put("sourceId", vo.getId());

			body.put("sourceUserName", vo.getSourceUserName());
			body.put("orgId", vo.getSourceOrgId());

			String url = "http://localhost:8021/api/ticketcontroller/createComments";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

			System.out.println("📤 B → A Payload: " + body);

			ResponseEntity<String> res = restTemplate.postForEntity(url, request, String.class);

			System.out.println("✅ B → A Response: " + res.getBody());

		} catch (Exception e) {
			System.out.println("❌ ERROR in B → A Sync");
			e.printStackTrace();
		}
	}
}
