package com.base.basesetup.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {

	void sendHtmlEmail(String fromEail, String toEmail, String subject, String htmlContent);
}
