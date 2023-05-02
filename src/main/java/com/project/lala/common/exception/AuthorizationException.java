package com.project.lala.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthorizationException extends IllegalStateException {
	private String message;
}
