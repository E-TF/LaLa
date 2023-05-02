package com.project.lala.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.lala.dto.MemberDeleteRequest;
import com.project.lala.entity.Member;
import com.project.lala.repository.MemberRepository;
import com.project.lala.service.AuthService;
import com.project.lala.service.MemberService;

@WebMvcTest(MemberController.class)
@AutoConfigureMockMvc
class MemberControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper = new ObjectMapper();

	@MockBean
	private MemberRepository memberRepository;

	@MockBean
	private MemberService memberService;

	@MockBean
	private AuthService authService;

	@Test
	@DisplayName("회원탈퇴 - 성공")
	void deleteMember_success() throws Exception {
		Long memberId = 1L;
		String password = "!@testPassword1234";
		Member member = new Member(memberId, "testLoginId", password, "testNickname", "testName",
			"testEmail@email.com");

		MockHttpSession session = new MockHttpSession();
		doNothing().when(authService).authorize(session, memberId);

		when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
		MemberDeleteRequest request = new MemberDeleteRequest(password);

		mockMvc.perform(delete("/members/{id}", member.getId())
				.session(session)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(request))
				.content("{\"password\": \"" + password + "\"}"))
			.andExpect(status().isNoContent());

		memberRepository.delete(member);

		verify(memberRepository, times(1)).delete(member);
		verify(authService, times(1)).authorize(session, member.getId());
		verify(memberService, times(1)).deleteMember(member.getId(), member.getPassword());
	}

	@Test
	@DisplayName("회원탈퇴 - 비밀번호 불일치")
	void deleteMember_passwordMismatch() throws Exception {
		String password = "!@testPassword1234";
		Member member = new Member(1L, "testLoginId", password, "testNickname", "testName", "testEmail@email.com");

		when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
		mockMvc.perform(delete("/members/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"password\": \"" + "wrongPassword" + "\"}"))
			.andExpect(status().isBadRequest());

		verify(memberRepository, times(0)).delete(member);
		verify(memberService, times(0)).deleteMember(member.getId(), member.getPassword());
	}

}
