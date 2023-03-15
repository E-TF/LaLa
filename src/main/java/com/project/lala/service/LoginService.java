package com.project.lala.service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.project.lala.common.constant.SessionConst;
import com.project.lala.common.encrytion.EncryptionService;
import com.project.lala.common.exception.AlreadyLoggedInException;
import com.project.lala.common.exception.InvalidPasswordException;
import com.project.lala.common.exception.NonExistentMemberException;
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

	public void login(LoginRequest loginRequest) {
		String encryptedPassword = encryptionService.encrypt(loginRequest.password());

		Member loginMember = memberRepository.findByLoginIdAndPassword(loginRequest.loginId(), encryptedPassword)
			.orElseThrow(NonExistentMemberException::new);

		if (httpSession.getAttribute(SessionConst.LOGIN_MEMBER) != null) {
			throw new AlreadyLoggedInException();
		}

		if (!loginMember.matchPassword(encryptedPassword)) {
			throw new InvalidPasswordException();
		}

		httpSession.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
		log.info("loginMember={}", loginMember);
	}

	public void logout(HttpSession httpSession) {
		httpSession.removeAttribute(SessionConst.LOGIN_MEMBER);
	}
}
