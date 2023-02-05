package com.project.lala.common.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	DUPLICATE_LOGIN_ID(BAD_REQUEST, "이미 존재하는 아이디 입니다."),
	DUPLICATE_EMAIL(BAD_REQUEST, "이미 존재하는 이메일 입니다.");
	private final HttpStatus httpStatus;
	private final String message;
}
