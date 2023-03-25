package com.project.lala.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.project.lala.common.constant.LoginRole;
import com.project.lala.common.constant.MemberStatus;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MEMBERS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, length = 30)
	private String loginId;

	@Column(nullable = false)
	private String password;

	@Column(length = 30)
	private String nickname;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false, length = 200)
	private String email;

	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate registeredAt;

	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate updatedAt;

	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate deletedAt;

	@Enumerated(EnumType.STRING)
	private MemberStatus memberStatus;

	private LoginRole role;

	@Builder
	public Member(Long id, String loginId, String password, String nickname, String name, String email,
		LoginRole role) {
		this.id = id;
		this.loginId = loginId;
		this.password = password;
		this.nickname = nickname;
		this.name = name;
		this.email = email;
		this.role = role;
		this.memberStatus = MemberStatus.UN_EMAIL_AUTH;
		this.registeredAt = LocalDate.now();
	}

	public static Member createMember(String loginId, String password, String nickname, String name, String email) {
		return Member.builder()
			.loginId(loginId)
			.password(password)
			.nickname(nickname)
			.name(name)
			.email(email)
			.build();
	}

	public void addStatus(MemberStatus memberStatus) {
		this.memberStatus = memberStatus;
	}

	public void updatePassword(String password) {
		this.password = password;
	}

	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}

	public void updateName(String name) {
		this.name = name;
	}

	public void updateEmail(String email) {
		this.email = email;
	}

	public boolean matchPassword(String password) {
		return this.password.equals(password);
	}
}
