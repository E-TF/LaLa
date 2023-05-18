package com.project.lala.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project.lala.common.encrytion.EncryptionService;
import com.project.lala.common.encrytion.SHA512EncryptionService;
import com.project.lala.entity.Member;
import com.project.lala.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private final EncryptionService encryptionService = new SHA512EncryptionService();

	@Mock
	private HttpSession session;

	@InjectMocks
	private AuthService authService;

	@DisplayName("로그인 - 올바른 아이디와 비밀번호를 입력한 경우")
	@Test
	void authorize() {
		Long memberId = 1L;
		String password = "password";
		Member member = Member.builder()
			.id(memberId)
			.password(password)
			.build();
		when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
		when(encryptionService.encrypt(password)).thenReturn(password);
		when(session.getAttribute("memberId")).thenReturn(memberId);

		authService.authenticate(session, memberId, password);
		assertEquals(memberId, session.getAttribute("memberId"));
	}
}
