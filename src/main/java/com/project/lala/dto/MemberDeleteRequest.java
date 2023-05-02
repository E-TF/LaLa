package com.project.lala.dto;

public record MemberDeleteRequest(String password) {

	public MemberDeleteRequest {
		String regex = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,30}";

		if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException("비밀번호를 입력해주세요.");
		}

		if (!password.matches(regex)) {
			throw new IllegalArgumentException(
				"비밀번호는 영문 대/소문자, 숫자, 특수 기호가 1개씩 포함 되어 있어야 하며, 최소 8자 ~ 최대 30자 이내로 입력해주세요.");
		}
	}

}
