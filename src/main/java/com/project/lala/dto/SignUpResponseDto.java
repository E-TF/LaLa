package com.project.lala.dto;

import java.time.LocalDate;

import com.project.lala.entity.Member;

public class SignUpResponseDto {

	private String loginId;
	private String password;
	private String nickname;
	private String name;
	private Member.Status status;
	private boolean isCertify;
	private LocalDate registeredAt;

	public SignUpResponseDto() {
	}

	public SignUpResponseDto(String loginId, String password, String nickname, String name,
		Member.Status status,
		boolean isCertify, LocalDate registeredAt) {
		this.loginId = loginId;
		this.password = password;
		this.nickname = nickname;
		this.name = name;
		this.status = status;
		this.isCertify = isCertify;
		this.registeredAt = registeredAt;
	}

	public String getLoginId() {
		return loginId;
	}

	public String getPassword() {
		return password;
	}

	public String getNickname() {
		return nickname;
	}

	public String getName() {
		return name;
	}

	public Member.Status getStatus() {
		return status;
	}

	public boolean isCertify() {
		return isCertify;
	}

	public LocalDate getRegisteredAt() {
		return registeredAt;
	}
}
