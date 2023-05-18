package com.project.lala.service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.project.lala.common.encrytion.EncryptionService;
import com.project.lala.common.exception.AuthorizationException;
import com.project.lala.entity.Member;
import com.project.lala.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;
	private final EncryptionService encryptionService;

	public void authenticate(HttpSession session, Long id, String password) throws AuthorizationException {
		Member member = memberRepository.findById(id)
			.orElseThrow(() -> new AuthorizationException("로그인 정보가 올바르지 않습니다."));

		if (!member.getPassword().equals(encryptionService.encrypt(password))) {
			throw new AuthorizationException("로그인 정보가 올바르지 않습니다.");
		}

		session.setAttribute("memberId", member.getId());
	}

	public void authorize(Long id) throws AuthorizationException {
		memberRepository.findById(id)
			.filter(member -> member.getId().equals(id))
			.orElseThrow(() -> new AuthorizationException("해당 정보에 대한 권한이 없습니다."));
	}

}
