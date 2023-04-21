package com.project.lala.controller;

import static org.assertj.core.api.Assertions.*;
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
import com.project.lala.dto.MemberUpdateRequest;
import com.project.lala.service.MemberService;

@WebMvcTest(MemberController.class)
@AutoConfigureMockMvc
class MemberControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MemberService memberService;

	EncryptionService encryptionService = new SHA512EncryptionService();

	@Test
	@DisplayName("회원 정보 수정 - 유효한 데이터")
	void updateMember_withValidData() throws Exception {
		Long memberId = 1L;
		String encryptedPassword = encryptionService.encrypt("change_password");
		String nickname = "change_nickname";

		MemberUpdateRequest request = new MemberUpdateRequest(encryptedPassword, nickname);

		mockMvc.perform(put("/members/{id}", memberId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(request)))
			.andExpect(status().isNoContent());

		verify(memberService).updateMember(memberId, encryptedPassword, nickname);
	}

	@Test
	@DisplayName("회원 정보 수정 - 유효하지 않은 데이터")
	void updateMember_withInvalidData() {
		Long memberId = 1L;
		String encryptedPassword = encryptionService.encrypt("invalid_password");
		String nickname = "invalid_nickname_with_very_long_length";

		assertThatThrownBy(() -> new MemberUpdateRequest(encryptedPassword, nickname))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("닉네임은 최대 30자까지 입력 가능합니다.");
	}

	@Test
	@DisplayName("비밀번호가 null인 경우")
	void updateMember_withNullPassword() {
		Long memberId = 1L;
		String nickname = "change_nickname";

		assertThatThrownBy(() -> new MemberUpdateRequest(null, nickname))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("비밀번호를 입력해주세요.");
	}

	@Test
	@DisplayName("닉네임이 null인 경우")
	void updateMember_withNullNickname() {
		Long memberId = 1L;
		String encryptedPassword = encryptionService.encrypt("change_password");

		assertThatThrownBy(() -> new MemberUpdateRequest(encryptedPassword, null))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("닉네임을 입력해주세요.");
	}

}
