package com.project.lala.controller;

import static com.project.lala.common.constant.UserType.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.lala.dto.LoginRequest;
import com.project.lala.service.LoginService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity<Void> login(@RequestBody @Valid LoginRequest loginRequest, HttpSession session) {
		loginService.login(loginRequest, MEMBER);
		session.setAttribute(MEMBER.name(), loginRequest.loginId());
		session.setMaxInactiveInterval(60 * 10);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpSession session) {
		session.invalidate();
		return ResponseEntity.ok().build();
	}
}
