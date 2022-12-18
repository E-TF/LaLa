package com.project.lala.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.lala.dto.SignUpRequestDto;
import com.project.lala.dto.SignUpResponseDto;
import com.project.lala.service.EmailService;
import com.project.lala.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	private final EmailService emailService;

	@PostMapping("/signup")
	public void signup(@RequestBody SignUpRequestDto signUpRequestDto) {
		log.info("members signup, signUpRequestDto: {}", signUpRequestDto);
		SignUpResponseDto signUpResponseDto = memberService.signUp(signUpRequestDto);
	}

	@GetMapping("/confirm-email")
	public void confirmEmail(@ModelAttribute SignUpRequestDto signUpRequestDto) {
		memberService.confirmEmail(signUpRequestDto);
	}
}
