package com.project.lala.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.lala.dto.MemberUpdateRequest;
import com.project.lala.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PutMapping("/members/{id}")
	public ResponseEntity<Void> updateMember(@PathVariable Long id, @RequestBody MemberUpdateRequest request) {
		memberService.updateMember(id, request.password(), request.nickname());
		return ResponseEntity.noContent().build();
	}

}
