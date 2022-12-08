package com.project.lala.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.lala.entity.Member;
import com.project.lala.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

	private final MemberRepository memberRepository;

	public void signUp(Member member) {
		validateDuplicateByLoginId(member.getLoginId());
		validateDuplicateByEmail(member.getEmail());
		memberRepository.save(member);
	}

	private void validateDuplicateByLoginId(String loginId) {
		if (memberRepository.findByLoginId(loginId) != null) {
			throw new IllegalStateException("이미 존재하는 아이디 입니다.");
		}
	}

	private void validateDuplicateByEmail(String email) {
		if (memberRepository.findByEmail(email) != null) {
			throw new IllegalStateException("이미 존재하는 이메일 입니다.");
		}
	}
}
