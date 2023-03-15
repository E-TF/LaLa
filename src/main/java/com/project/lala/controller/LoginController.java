package com.project.lala.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.lala.common.constant.SessionConst;
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
	public void login(@RequestBody @Valid LoginRequest loginRequest) {
		loginService.login(loginRequest);
	}

	@PostMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(SessionConst.LOGIN_MEMBER);
		return "redirect:/";
	}
}
