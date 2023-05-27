package com.project.lala.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.lala.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@DeleteMapping("/members")
	public ResponseEntity<Void> deleteMember(HttpSession session) {
		Optional<Long> loginMember = Optional.ofNullable((Long)session.getAttribute("memberId"));
		Long memberId = loginMember.orElseThrow(() -> new IllegalStateException("로그인 정보가 없습니다."));

		memberService.deleteMember(memberId);
		session.invalidate();
		return ResponseEntity.noContent().build();
	}

}
