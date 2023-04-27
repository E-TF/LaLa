package com.project.lala.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginFailedException extends IllegalArgumentException {
	private static final String message = "아이디 또는 비밀번호를 확인해 주세요.";
}
