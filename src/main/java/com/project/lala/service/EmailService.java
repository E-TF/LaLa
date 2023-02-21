package com.project.lala.service;

import java.time.LocalDateTime;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.lala.dto.SignUpRequest;
import com.project.lala.entity.EmailAuth;
import com.project.lala.repository.EmailAuthRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender javaMailSender;

	private final EmailAuthRepository emailAuthRepository;

	private final SignUpRequest signUpRequest;

	@Async
	public void sendEmail(String email, String authToken) {
		String lala = "http://localhost:8080";
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(email);
		mailMessage.setSubject("회원가입 이메일 인증");
		mailMessage.setText(
			lala + "/sign/confirm-email?email=" + email + "&authToken=" + authToken);

		javaMailSender.send(mailMessage);
	}

	@Transactional
	public void confirmEmail(String email, String authToken) {
		EmailAuth emailAuth = emailAuthRepository.findValidAuthByEmail(email, authToken, LocalDateTime.now())
			.orElseThrow(IllegalArgumentException::new);
		emailAuth.useEmailAuthToken();
	}
}
