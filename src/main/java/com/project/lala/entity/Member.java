package com.project.lala.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.project.lala.common.member.MemberStatus;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MEMBER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "login_id", nullable = false, length = 30)
	private String loginId;

	@Column(nullable = false)
	private String password;

	@Column(length = 30)
	private String nickname;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false, length = 200)
	private String email;

	@Column(name = "registered_at", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate registeredAt;

	@Column(name = "updated_at")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate updatedAt;

	@Column(name = "deleted_at")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate deletedAt;

	@ElementCollection(fetch = FetchType.LAZY)
	@Enumerated(EnumType.STRING)
	private List<MemberStatus> memberStatuses = new ArrayList<>();

	@Builder
	public Member(Long id, String loginId, String password, String nickname, String name, String email,
		List<MemberStatus> memberStatuses) {
		this.id = id;
		this.loginId = loginId;
		this.password = password;
		this.nickname = nickname;
		this.name = name;
		this.email = email;
		this.memberStatuses = Collections.singletonList(MemberStatus.UNEMAILAUTH);
		this.registeredAt = LocalDate.now();
	}

	public static Member createMember(String loginId, String password, String nickname, String name, String email) {
		Member member = new Member();
		member.loginId = loginId;
		member.password = password;
		member.nickname = nickname;
		member.name = name;
		member.email = email;
		member.registeredAt = LocalDate.now();
		return member;
	}

	public void addStatus(MemberStatus memberStatus) {
		this.memberStatuses.add(memberStatus);
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
}
