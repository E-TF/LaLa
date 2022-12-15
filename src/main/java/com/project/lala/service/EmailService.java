package com.project.lala.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@EnableAsync
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender javaMailSender;

	@Async
	public void sendEmail(String email, String authToken) {
		String lala = "http://localhost:8080";
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(email);
		mailMessage.setSubject("회원가입 이메일 인증");
		mailMessage.setText(
			lala + "/api/members/confirm-email?email=" + email + "&authToken=" + authToken);

		javaMailSender.send(mailMessage);
	}
}
