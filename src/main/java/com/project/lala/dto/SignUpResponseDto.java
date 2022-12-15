package com.project.lala.dto;

import java.time.LocalDate;

import com.project.lala.entity.Member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpResponseDto {

	private Long id;
	private String loginId;
	private String password;
	private String nickname;
	private String name;
	private String email;
	private LocalDate registeredAt;

	@Builder
	public SignUpResponseDto(Long id, String loginId, String password, String nickname, String name, String email) {
		this.id = id;
		this.loginId = loginId;
		this.password = password;
		this.nickname = nickname;
		this.name = name;
		this.email = email;
		this.registeredAt = LocalDate.now();
	}

	public static SignUpResponseDto signUpResponseDto(Member member) {
		return SignUpResponseDto.builder()
			.id(member.getId())
			.loginId(member.getLoginId())
			.password(member.getPassword())
			.nickname(member.getNickname())
			.name(member.getName())
			.email(member.getEmail())
			.build();
	}
}
