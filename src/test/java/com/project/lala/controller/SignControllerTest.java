package com.project.lala.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import com.project.lala.dto.SignUpRequest;
import com.project.lala.service.EmailService;
import com.project.lala.service.MemberService;

@WebMvcTest(SignController.class)
@AutoConfigureMockMvc
class SignControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper = new ObjectMapper();

	@MockBean
	private MemberService memberService;

	@MockBean
	private EmailService emailService;

	EncryptionService encryptionService = new SHA512EncryptionService();

	@Test
	@DisplayName("회원 가입 - 정상인 경우 status 201 반환")
	void signup_success() throws Exception {

		SignUpRequest request = SignUpRequest.builder()
			.loginId("test_id")
			.password(encryptionService.encrypt("!@#test123"))
			.nickname("test_nick")
			.name("test_name")
			.email("test_email@email.test")
			.build();

		mockMvc.perform(
				post("/register")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isCreated());

		verify(memberService, times(1)).signUp(any(SignUpRequest.class));
	}

	@Test
	@DisplayName("이메일 인증 요청이 정상 확인 된 경우 status 200 반환 ")
	void confirmEmail_success() throws Exception {

		String email = "test_email@email.test";
		String authToken = "test_token";

		mockMvc.perform(
				get("/confirm-email")
					.param("email", email)
					.param("authToken", authToken))
			.andExpect(status().isOk());

		verify(emailService, times(1)).confirmEmail(email, authToken);
	}

}
