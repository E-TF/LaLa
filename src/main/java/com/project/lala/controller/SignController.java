package com.project.lala.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.lala.dto.SignUpRequest;
import com.project.lala.service.EmailService;
import com.project.lala.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/sign")
@RequiredArgsConstructor
public class SignController {

	private final MemberService memberService;
	private final EmailService emailService;

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public void signup(@RequestBody SignUpRequest signUpRequest) {
		log.info("members signup, signUpRequest: {}", signUpRequest);
		memberService.signUp(signUpRequest);
	}

	@GetMapping("/confirm-email")
	public void confirmEmail(@ModelAttribute SignUpRequest signUpRequest) {
		emailService.confirmEmail(signUpRequest);
	}
}
