package com.project.lala.dto;

public record LoginRequest(String loginId, String password) {
	public LoginRequest {
		if (loginId == null || loginId.isEmpty()) {
			throw new IllegalArgumentException("아이디를 입력해주세요.");
		} else if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException("비밀번호를 입력해주세요.");
		}
	}
}
