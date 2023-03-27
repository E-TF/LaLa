package com.project.lala.controller;

import static com.project.lala.common.constant.UserType.*;
import static org.mockito.BDDMockito.*;
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
import com.project.lala.dto.LoginRequest;
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
	void login_success() throws Exception {
		String loginId = "test_loginId";
		String password = "test_password";

		LoginRequest loginRequest = new LoginRequest(loginId, password);

		mockMvc.perform(post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(loginRequest)))
			.andExpect(status().isOk());

		verify(loginService).login(loginRequest, MEMBER);
	}

	@Test
	@DisplayName("로그아웃")
	void logout() throws Exception {
		MockHttpSession mockHttpSession = new MockHttpSession();
		mockHttpSession.setAttribute(MEMBER.name(), "test_session");

		mockMvc.perform(post("/logout")
				.session(mockHttpSession))
			.andExpect(status().isOk());
	}

}
