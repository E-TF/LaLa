package com.project.lala.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class SignUpRequest {

	@Size(min = 6, max = 30, message = "아이디는 최소 6자 이상 이어야 합니다.")
	@NotBlank(message = "아이디를 입력해주세요.")
	private String loginId;

	@NotBlank(message = "비밀번호를 입력해주세요.")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,30}",
		message = "비밀번호는 영문 대/소문자, 숫자, 특수 기호가 1개씩 포함 되어 있어야 하며, 최소 8자 ~ 최대 30자 이내로 입력해주세요.")
	private String password;

	@Size(max = 30)
	@Nullable
	private String nickname;

	@Size(max = 100)
	@NotBlank(message = "이름을 입력해주세요.")
	private String name;

	@NotBlank(message = "이메일을 입력해주세요.")
	@Pattern(regexp = "[a-zA-z0-9]+@[a-zA-z]+[.]+[a-zA-z.]+", message = "이메일 형식을 올바르게 입력해주세요.")
	private String email;

	private String authToken;

}
