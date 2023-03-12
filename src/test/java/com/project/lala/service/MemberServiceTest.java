package com.project.lala.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project.lala.common.encrytion.EncryptionService;
import com.project.lala.common.encrytion.SHA512EncryptionService;
import com.project.lala.common.exception.EmailDuplicationException;
import com.project.lala.common.exception.MemberDuplicationException;
import com.project.lala.dto.SignUpRequest;
import com.project.lala.dto.SignUpResponse;
import com.project.lala.entity.EmailAuth;
import com.project.lala.entity.Member;
import com.project.lala.repository.EmailAuthRepository;
import com.project.lala.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

	@Mock
	private MemberRepository memberRepository;

	private final EmailAuthRepository emailAuthRepository = Mockito.mock(EmailAuthRepository.class);

	@Mock
	private EmailService emailService;

	@InjectMocks
	private MemberService memberService;

	private final EncryptionService encryptionService = new SHA512EncryptionService();

	@Test
	@DisplayName("회원가입 - 정상")
	void signUp_success() {
		SignUpRequest request = request();
		Member member = Member.builder().id(1L).build();

		when(memberRepository.findByLoginId(anyString())).thenReturn(Optional.empty());
		when(memberRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		when(emailAuthRepository.save(any(EmailAuth.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
		when(memberRepository.save(any())).thenReturn(member);

		SignUpResponse response = memberService.signUp(request);

		assertNotNull(response);
		assertNotNull(response.getId());

		verify(memberRepository, times(1)).findByLoginId(request.getLoginId());
		verify(memberRepository, times(1)).findByEmail(request.getEmail());
		verify(emailAuthRepository, times(1)).save(any(EmailAuth.class));
		verify(memberRepository, times(1)).save(any(Member.class));
		verify(emailService, times(1)).sendEmail(eq(request.getEmail()), anyString());

		ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);
		verify(memberRepository).save(captor.capture());

		Member savedMember = captor.getValue();
		assertEquals(request.getLoginId(), savedMember.getLoginId());
		assertEquals(request.getNickname(), savedMember.getNickname());
		assertEquals(request.getName(), savedMember.getName());
		assertEquals(request.getEmail(), savedMember.getEmail());
	}

	@Test
	@DisplayName("회원가입 - 중복 아이디")
	void signUp_duplicateLoginId() {
		SignUpRequest request = request();

		when(memberRepository.findByLoginId(anyString())).thenReturn(Optional.of(Member.builder().build()));

		assertThrows(MemberDuplicationException.class, () -> memberService.signUp(request));
		verify(memberRepository, times(1)).findByLoginId(request.getLoginId());
		verify(memberRepository, times(0)).findByEmail(request.getEmail());
		verify(emailAuthRepository, times(0)).save(any(EmailAuth.class));
		verify(memberRepository, times(0)).save(any(Member.class));
		verify(emailService, times(0)).sendEmail(anyString(), anyString());
	}

	@Test
	@DisplayName("회원가입 - 중복 이메일")
	void signUp_duplicateEmail() {
		SignUpRequest request = request();

		when(memberRepository.findByLoginId(anyString())).thenReturn(Optional.empty());
		when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(Member.builder().build()));

		assertThrows(EmailDuplicationException.class, () -> memberService.signUp(request));
		verify(memberRepository, times(1)).findByLoginId(request.getLoginId());
		verify(memberRepository, times(1)).findByEmail(request.getEmail());
		verify(emailAuthRepository, times(0)).save(any(EmailAuth.class));
		verify(memberRepository, times(0)).save(any(Member.class));
		verify(emailService, times(0)).sendEmail(anyString(), anyString());
	}

	private SignUpRequest request() {
		return SignUpRequest.builder()
			.loginId("test_id")
			.password(encryptionService.encrypt("!@#test123"))
			.nickname("test_nick")
			.name("test_name")
			.email("test_email@email.test")
			.build();
	}

}
