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
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.lala.common.encrytion.EncryptionService;
import com.project.lala.common.encrytion.SHA512EncryptionService;
import com.project.lala.entity.Member;
import com.project.lala.repository.MemberRepository;
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

	EncryptionService encryptionService = new SHA512EncryptionService();

	@Test
	@DisplayName("회원탈퇴 - 성공")
	void deleteMember_success() throws Exception {
		String password = encryptionService.encrypt("testPassword");
		Member member = new Member(1L, "testLoginId", password, "testNickname", "testName", "testEmail@email.com");

		when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

		mockMvc.perform(delete("/members/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"password\": \"" + password + "\"}"))
			.andExpect(status().isNoContent());

		memberRepository.delete(member);

		verify(memberRepository, times(1)).delete(member);
	}

}
