package com.project.lala.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.lala.dto.SignUpRequestDto;
import com.project.lala.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@PostMapping("/signup")
	public void signup(@RequestBody SignUpRequestDto signUpRequestDto) {
		log.info("members signup, signUpRequestDto: {}", signUpRequestDto);
		memberService.signUp(signUpRequestDto.createMember());
	}
}