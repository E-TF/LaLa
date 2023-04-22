package com.project.lala.dto;

public record MemberDeleteRequest(String password) {

	public MemberDeleteRequest {
		if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException("비밀번호를 입력해주세요.");
		}
	}

}
