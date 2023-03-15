package com.project.lala.controller;

import static org.junit.jupiter.api.Assertions.*;
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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.lala.common.constant.SessionConst;
import com.project.lala.dto.LoginRequest;
import com.project.lala.entity.Member;
import com.project.lala.service.LoginService;

@WebMvcTest(LoginController.class)
@AutoConfigureMockMvc
class LoginControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LoginService loginService;

	@Test
	@DisplayName("로그인")
	void login() throws Exception {
		String loginId = "test_loginId";
		String password = "test_password";

		LoginRequest loginRequest = new LoginRequest(loginId, password);

		mockMvc.perform(post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(loginRequest)))
			.andExpect(status().isOk());

		verify(loginService).login(loginRequest);
	}

	@Test
	@DisplayName("로그아웃")
	void logout() throws Exception {
		Member member = Member.builder()
			.id(1L)
			.loginId("test_loginId")
			.password("test_password")
			.build();

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(SessionConst.LOGIN_MEMBER, member);

		mockMvc.perform(post("/logout")
				.session(session))
			.andExpect(status().isOk());

		assertNull(session.getAttribute(SessionConst.LOGIN_MEMBER));
	}

}
