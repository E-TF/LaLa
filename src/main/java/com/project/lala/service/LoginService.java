package com.project.lala.service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.project.lala.common.constant.UserType;
import com.project.lala.common.encrytion.EncryptionService;
import com.project.lala.common.exception.AlreadyLoggedInException;
import com.project.lala.common.exception.LoginFailedException;
import com.project.lala.dto.LoginRequest;
import com.project.lala.entity.Member;
import com.project.lala.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

	private final MemberRepository memberRepository;
	private final HttpSession httpSession;
	private final EncryptionService encryptionService;

	public void login(LoginRequest loginRequest, UserType role) {
		Member member = (Member)httpSession.getAttribute(role.name());

		if (member != null) {
			throw new AlreadyLoggedInException();
		}

		String encryptedPassword = encryptionService.encrypt(loginRequest.password());

		Member loginMember = memberRepository.findByLoginIdAndPassword(loginRequest.loginId(), encryptedPassword)
			.orElseThrow(LoginFailedException::new);

		httpSession.setAttribute(loginMember.getRole().name(), loginMember);
		log.info("loginMember={}", loginMember);
	}

	public void logout(UserType role) {
		if (httpSession == null) {
			throw new IllegalStateException();
		}

		httpSession.removeAttribute(role.name());
	}

}
