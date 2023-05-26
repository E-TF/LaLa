package com.project.lala.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.lala.entity.Member;
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
		Member sessionMemberId = (Member)session.getAttribute("memberId");
		Long memberId = sessionMemberId.getId();
		memberService.deleteMember(memberId);
		return ResponseEntity.noContent().build();
	}

}
