package com.project.lala.service;

import org.springframework.stereotype.Service;

import com.project.lala.entity.Member;
import com.project.lala.repository.MemberRepository;

@Service
public class MemberService {

	private final MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public Long signUp(Member member) {
		validateDuplicateById(member);
		validateDuplicateByEmail(member);
		memberRepository.save(member);
		return member.getId();
	}

	private void validateDuplicateById(Member member) {
		if (!memberRepository.findById(member.getLoginId()).isEmpty()) {
			throw new IllegalStateException("이미 존재하는 아이디 입니다.");
		}
	}

	private void validateDuplicateByEmail(Member member) {
		if (!memberRepository.findByEmail(member.getEmail()).isEmpty()) {
			throw new IllegalStateException("이미 존재하는 이메일 입니다.");
		}
	}
}
