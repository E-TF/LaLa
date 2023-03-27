package com.project.lala.common.exception;

public class AlreadyLoggedInException extends IllegalStateException {
	public AlreadyLoggedInException() {
		super("이미 로그인이 되어 있습니다.");
	}
}
