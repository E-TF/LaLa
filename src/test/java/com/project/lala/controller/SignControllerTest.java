package com.project.lala.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.lala.common.encrytion.EncryptionService;
import com.project.lala.dto.SignUpResponse;
import com.project.lala.service.MemberService;

@SpringBootTest
@AutoConfigureMockMvc
class SignControllerTest {

	@MockBean
	private MemberService memberService;

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private EncryptionService encryptionService;

	ObjectMapper objectMapper = new ObjectMapper();

	@Test
	@DisplayName("회원이 생성 될 경우 status 200 반환")
	void memberSaveTest() throws Exception {

		doReturn(responseMember()).when(memberService).signUp(any());

		mockMvc.perform(
				post("/sign/register")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.characterEncoding("UTF-8")
					.content(objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(responseMember())))
			.andExpect(status().isCreated());
	}
	
	private SignUpResponse responseMember() {
		return SignUpResponse.builder()
			.loginId("login_id")
			.email("login_id_email@email.test")
			.name("login_id_name")
			.password(encryptionService.encrypt("loginIdTest1234!@#"))
			.build();
	}
}
