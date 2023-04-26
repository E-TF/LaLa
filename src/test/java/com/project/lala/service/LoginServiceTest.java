package com.project.lala.service;

import static com.project.lala.common.constant.UserType.*;
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

import com.project.lala.common.encrytion.EncryptionService;
import com.project.lala.common.exception.AlreadyLoggedInException;
import com.project.lala.common.exception.LoginFailedException;
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

		loginService.login(loginRequest, member.getRole());

		verify(httpSession).setAttribute(MEMBER.name(), member);
	}

	@Test
	@DisplayName("로그인 실패 - 존재하지 않은 회원 로그인")
	void login_nonExistentMember() {
		String loginId = "test_loginId";
		String password = "test_password";

		LoginRequest loginRequest = new LoginRequest(loginId, password);

		when(encryptionService.encrypt(password)).thenReturn(password);
		when(memberRepository.findByLoginIdAndPassword(loginId, password)).thenReturn(Optional.empty());

		assertThrows(LoginFailedException.class, () -> loginService.login(loginRequest, MEMBER));

		verify(memberRepository).findByLoginIdAndPassword(loginId, password);
	}

	@Test
	@DisplayName("로그인 실패 - 비밀번호가 일치하지 않음")
	void login_invalid_password() {
		String loginId = "test_loginId";
		String wrongPassword = "wrong_password";

		LoginRequest loginRequest = new LoginRequest(loginId, wrongPassword);

		when(memberRepository.findByLoginIdAndPassword(loginId, encryptionService.encrypt(wrongPassword)))
			.thenReturn(Optional.empty());

		assertThrows(LoginFailedException.class, () -> loginService.login(loginRequest, MEMBER));
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

		when(httpSession.getAttribute(MEMBER.name())).thenReturn(member);
		assertThrows(AlreadyLoggedInException.class, () -> loginService.login(loginRequest, member.getRole()));
	}

	@Test
	@DisplayName("아이디만 입력하지 않은 경우")
	void login_id_empty() {
		String loginId = "";
		String password = "test_password";

		assertThrows(IllegalArgumentException.class,
			() -> loginService.login(new LoginRequest(loginId, password), MEMBER));
	}

	@Test
	@DisplayName("패스워드만 입력하지 않은 경우")
	void login_password_empty() {
		String loginId = "test_loginId";
		String password = "";

		assertThrows(IllegalArgumentException.class,
			() -> loginService.login(new LoginRequest(loginId, password), MEMBER));
	}

	@Test
	@DisplayName("아이디, 비밀번호를 모두 입력하지 않은 경우")
	void login_all_empty() {
		String loginId = "";
		String password = "";

		assertThrows(IllegalArgumentException.class,
			() -> loginService.login(new LoginRequest(loginId, password), MEMBER));
	}

	@Test
	@DisplayName("로그아웃")
	void logout() {
		Member member = Member.builder()
			.id(1L)
			.loginId("test_loginId")
			.password("test_password")
			.build();

		httpSession.setAttribute(MEMBER.name(), member);
		loginService.logout(MEMBER);

		assertNull(httpSession.getAttribute(MEMBER.name()));
	}

}
