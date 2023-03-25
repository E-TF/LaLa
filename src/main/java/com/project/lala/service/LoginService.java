package com.project.lala.service;

import static com.project.lala.common.constant.LoginRole.*;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.lala.common.constant.LoginRole;
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

	public void login(LoginRequest loginRequest, LoginRole role) {
		String encryptedPassword = encryptionService.encrypt(loginRequest.password());

		Member loginMember = memberRepository.findByLoginIdAndPassword(loginRequest.loginId(), encryptedPassword)
			.orElseThrow(NonExistentMemberException::new);

		role = loginMember.getRole();

		if (role == null) {
			throw new IllegalStateException("역할의 정보 및 정의가 되어 있지 않습니다. " + loginMember.getId());
		}

		checkAlreadyLoggedIn(role);

		if (!loginMember.matchPassword(encryptedPassword)) {
			throw new InvalidPasswordException();
		}

		httpSession.setAttribute(role.name(), loginMember);
		log.info("loginMember={}", loginMember);
	}

	public ResponseEntity<Void> logout() {
		httpSession.removeAttribute(MEMBER.name());
		return ResponseEntity.ok().build();
	}

	private void checkAlreadyLoggedIn(LoginRole loginRole) {
		if (loginRole == null || httpSession.getAttribute(loginRole.name()) != null) {
			throw new AlreadyLoggedInException();
		}
	}

}
