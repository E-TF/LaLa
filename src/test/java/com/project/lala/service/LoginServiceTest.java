package com.project.lala.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project.lala.common.constant.SessionConst;
import com.project.lala.common.encrytion.EncryptionService;
import com.project.lala.common.exception.AlreadyLoggedInException;
import com.project.lala.common.exception.InvalidPasswordException;
import com.project.lala.common.exception.NonExistentMemberException;
import com.project.lala.dto.LoginRequest;
import com.project.lala.entity.Member;
import com.project.lala.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private HttpSession httpSession;

	@InjectMocks
	private LoginService loginService;

	@Mock
	private EncryptionService encryptionService;

	@Test
	@DisplayName("로그인 성공")
	void login_success() {
		String loginId = "test_loginId";
		String password = "test_password";
		String encryptedPassword = "test_encryptedPassword";

		Member member = Member.builder()
			.id(1L)
			.loginId(loginId)
			.password(encryptedPassword)
			.build();

		LoginRequest loginRequest = new LoginRequest(loginId, password);

		when(encryptionService.encrypt(password)).thenReturn(encryptedPassword);
		when(memberRepository.findByLoginIdAndPassword(loginId, encryptedPassword)).thenReturn(Optional.of(member));

		loginService.login(loginRequest);

		verify(httpSession).setAttribute(SessionConst.LOGIN_MEMBER, member);
	}

	@Test
	@DisplayName("로그인 실패 - 존재하지 않은 회원 로그인")
	void login_nonExistentMember() {
		String loginId = "test_loginId";
		String password = "test_password";

		LoginRequest loginRequest = new LoginRequest(loginId, password);

		when(encryptionService.encrypt(password)).thenReturn(password);
		when(memberRepository.findByLoginIdAndPassword(loginId, password)).thenReturn(Optional.empty());

		assertThrows(NonExistentMemberException.class, () -> loginService.login(loginRequest));

		verify(memberRepository).findByLoginIdAndPassword(loginId, password);
	}

	@Test
	@DisplayName("로그인 실패 - 비밀번호가 일치하지 않음")
	void login_invalid_password() {
		String loginId = "test_loginId";
		String password = "invalid_password";
		String encryptedPassword = "test_encryptedPassword";

		Member member = Member.builder()
			.id(1L)
			.loginId(loginId)
			.password(password)
			.build();

		LoginRequest loginRequest = new LoginRequest(loginId, password);

		when(encryptionService.encrypt(password)).thenReturn(encryptedPassword);
		when(memberRepository.findByLoginIdAndPassword(loginId, encryptedPassword)).thenReturn(Optional.of(member));

		assertThrows(InvalidPasswordException.class, () -> loginService.login(loginRequest));
	}

	@Test
	@DisplayName("로그인 실패 - 이미 로그인된 경우")
	void login_alreadyLoggedIn() {
		String loginId = "test_loginId";
		String password = "test_password";
		String encryptedPassword = "test_encryptedPassword";

		Member member = Member.builder()
			.id(1L)
			.loginId(loginId)
			.password(encryptedPassword)
			.build();

		LoginRequest loginRequest = new LoginRequest(loginId, password);

		when(encryptionService.encrypt(password)).thenReturn(encryptedPassword);
		when(memberRepository.findByLoginIdAndPassword(loginId, encryptedPassword)).thenReturn(Optional.of(member));
		when(httpSession.getAttribute(SessionConst.LOGIN_MEMBER)).thenReturn(member);

		assertThrows(AlreadyLoggedInException.class, () -> loginService.login(loginRequest));

		verify(memberRepository).findByLoginIdAndPassword(loginId, encryptedPassword);
		verify(httpSession).getAttribute(SessionConst.LOGIN_MEMBER);
	}

	@Test
	@DisplayName("아이디만 입력하지 않은 경우")
	void login_id_empty() {
		String loginId = "";
		String password = "test_password";

		LoginRequest loginRequest = new LoginRequest(loginId, password);

		assertThrows(IllegalArgumentException.class, () -> loginService.login(loginRequest));
	}

	@Test
	@DisplayName("패스워드만 입력하지 않은 경우")
	void login_password_empty() {
		String loginId = "test_loginId";
		String password = "";

		LoginRequest loginRequest = new LoginRequest(loginId, password);

		assertThrows(IllegalArgumentException.class, () -> loginService.login(loginRequest));
	}

	@Test
	@DisplayName("아이디, 비밀번호를 모두 입력하지 않은 경우")
	void login_all_empty() {
		String loginId = "";
		String password = "";

		LoginRequest loginRequest = new LoginRequest(loginId, password);

		assertThrows(IllegalArgumentException.class, () -> loginService.login(loginRequest));
	}

	@Test
	@DisplayName("로그아웃")
	void logout() {
		Member member = Member.builder()
			.id(1L)
			.loginId("test_loginId")
			.password("test_password")
			.build();

		httpSession.setAttribute(SessionConst.LOGIN_MEMBER, member);

		loginService.logout(httpSession);

		assertNull(httpSession.getAttribute(SessionConst.LOGIN_MEMBER));
	}

}
