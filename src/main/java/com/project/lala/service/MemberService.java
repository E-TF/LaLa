package com.project.lala.service;

import org.springframework.stereotype.Service;

import com.project.lala.dto.SignUpResponseDto;
import com.project.lala.entity.Member;
import com.project.lala.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public SignUpResponseDto signUp(Member member) {
		validateDuplicateByLoginId((Member)memberRepository.findByLoginId(member.getLoginId()));
		validateDuplicateByEmail((Member)memberRepository.findByEmail(member.getEmail()));
		memberRepository.save(member);
		return (SignUpResponseDto)memberRepository;
	}

	private void validateDuplicateByLoginId(Member member) {
		if (!memberRepository.findByLoginId(member.getLoginId()).isEmpty()) {
			throw new IllegalStateException("이미 존재하는 아이디 입니다.");
		}
	}

	private void validateDuplicateByEmail(Member member) {
		if (!memberRepository.findByEmail(member.getEmail()).isEmpty()) {
			throw new IllegalStateException("이미 존재하는 이메일 입니다.");
		}
	}
}
