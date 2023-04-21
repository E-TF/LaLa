package com.project.lala.dto;

public record MemberUpdateRequest(String password, String nickname) {

	public MemberUpdateRequest {
		if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException("비밀번호를 입력해주세요.");
		}

		if (nickname == null || nickname.isEmpty()) {
			throw new IllegalArgumentException("닉네임을 입력해주세요.");
		}

		if (nickname.length() > 30) {
			throw new IllegalArgumentException("닉네임은 최대 30자까지 입력 가능합니다.");
		}
	}
}
