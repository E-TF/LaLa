package com.project.lala.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "MEMBER")
public class Member {

	public enum Status {
		DEFAULT, DELETED
	}

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "login_id")
	@NonNull
	private String loginId;

	@NonNull
	private String password;

	private String nickname;

	@NonNull
	private String name;

	@NonNull
	private String email;

	private Status status;

	@Column(name = "is_certify")
	private boolean isCertify;

	@Column(name = "is_leave")
	private boolean isLeave;

	@Column(name = "registered_at")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate registeredAt;

	@Column(name = "updated_at")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate updatedAt;

	@Column(name = "deleted_at")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate deletedAt;

	public Member() {
	}

	public Member(Long id, @NonNull String loginId, @NonNull String password, String nickname, @NonNull String name,
		@NonNull String email, Status status, boolean isCertify, boolean isLeave, LocalDate registeredAt,
		LocalDate updatedAt, LocalDate deletedAt) {
		this.id = id;
		this.loginId = loginId;
		this.password = password;
		this.nickname = nickname;
		this.name = name;
		this.email = email;
		this.status = status;
		this.isCertify = isCertify;
		this.isLeave = isLeave;
		this.registeredAt = registeredAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}

	public static Member createMember(String loginId, String password, String nickname, String name, String email,
		LocalDate registeredAt) {
		Member member = new Member();
		member.loginId = loginId;
		member.password = password;
		member.nickname = nickname;
		member.name = name;
		member.email = email;
		member.registeredAt = registeredAt;
		return member;
	}

	public void updateMember(Long id, @NonNull String loginId, @NonNull String password, String nickname,
		@NonNull String name, @NonNull String email, Status status, boolean isCertify, boolean isLeave,
		LocalDate registeredAt, LocalDate updatedAt, LocalDate deletedAt) {
		this.id = id;
		this.loginId = loginId;
		this.password = password;
		this.nickname = nickname;
		this.name = name;
		this.email = email;
		this.status = status;
		this.isCertify = isCertify;
		this.isLeave = isLeave;
		this.registeredAt = registeredAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}

	public Long getId() {
		return id;
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

	public String getEmail() {
		return email;
	}

	public Status getStatus() {
		return status;
	}

	public boolean isCertify() {
		return isCertify;
	}

	public boolean isLeave() {
		return isLeave;
	}

	public LocalDate getRegisteredAt() {
		return registeredAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public LocalDate getDeletedAt() {
		return deletedAt;
	}

	public void withdraw(boolean isLeave) {
		this.isLeave = isLeave;
		this.deletedAt = LocalDate.now();
	}
}
