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
	        System.out.println("🚀 Sending B → A for ID: " + vo.getId());

	        Map<String, Object> body = new HashMap<>();

	        body.put("comments", vo.getComment());
//	        body.put("userName", vo.getCommentName());
	        body.put("sourceTicketId", vo.getTicketId());
	        body.put("sourceId", vo.getId());
	        body.put("sourceUserName", vo.getCommentName());
	        body.put("sourceOrgId", vo.getOrgId());
	        body.put("ticketId", vo.getTicketId());

//	        String url = "http://localhost:8021/api/ticketcontroller/createComments";
	        String url = "http://139.5.190.203:8021/api/ticketcontroller/createComments";

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        HttpEntity<Map<String, Object>> request =
	                new HttpEntity<>(body, headers);

	        System.out.println("➡️ B → A Payload: " + body);

	        ResponseEntity<String> response =
	                restTemplate.postForEntity(url, request, String.class);

	        System.out.println("✅ B → A Response: " + response.getBody());

	    } catch (Exception e) {
	        System.out.println("❌ ERROR B → A");
	        e.printStackTrace();
	    }
	}
}
