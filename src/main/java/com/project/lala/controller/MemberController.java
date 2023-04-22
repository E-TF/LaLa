package com.project.lala.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.lala.dto.MemberDeleteRequest;
import com.project.lala.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@DeleteMapping("/members/{id}")
	public ResponseEntity<Void> deleteMember(@PathVariable Long id, @RequestBody MemberDeleteRequest request) {
		memberService.deleteMember(id, request.password());
		return ResponseEntity.noContent().build();
	}

}
