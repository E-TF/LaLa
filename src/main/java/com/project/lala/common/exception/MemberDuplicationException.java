package com.project.lala.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDuplicationException extends RuntimeException {
	private final ErrorCode errorCode;
	private final String message;
}
