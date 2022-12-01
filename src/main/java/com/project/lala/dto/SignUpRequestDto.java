package com.project.lala.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import com.project.lala.entity.Member;

public class SignUpRequestDto {

	@Column(name = "login_id")
	@Size(min = 6, max = 50, message = "아이디는 최소 6자 이어야 합니다.")
	@NotBlank(message = "아이디를 입력해주세요.")
	private String loginId;

	@NotBlank(message = "비밀번호를 입력해주세요.")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,30}",
		message = "비밀번호는 영문 대/소문자, 숫자, 특수 기호가 1개씩 포함 되어 있어야 하며, 최소 8자 ~ 최대 30자 이내로 입력해주세요.")
	private String password;

	@Size(max = 30)
	@Nullable
	private String nickname;

	@Size(max = 100)
	@NotBlank(message = "이름을 입력해주세요.")
	private String name;

	@NotBlank(message = "이메일을 입력해주세요.")
	@Pattern(regexp = "[a-zA-z0-9]+@[a-zA-z]+[.]+[a-zA-z.]+", message = "이메일 형식을 올바르게 입력해주세요.")
	private String email;

	private Member.Status status = Member.Status.DEFAULT;

	@Column(name = "is_certify")
	private boolean isCertify;

	@Column(name = "registered_at")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate registeredAt;

	public SignUpRequestDto(String loginId, String password, @Nullable String nickname, String name, String email,
		Member.Status status, boolean isCertify, LocalDate registeredAt) {
		this.loginId = loginId;
		this.password = password;
		this.nickname = nickname;
		this.name = name;
		this.email = email;
		this.status = status;
		this.isCertify = isCertify;
		this.registeredAt = registeredAt;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Nullable
	public String getNickname() {
		return nickname;
	}

	public void setNickname(@Nullable String nickname) {
		this.nickname = nickname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Member.Status getStatus() {
		return status;
	}

	public void setStatus(Member.Status status) {
		this.status = status;
	}

	public boolean isCertify() {
		return isCertify;
	}

	public void setCertify(boolean certify) {
		isCertify = certify;
	}

	public LocalDate getRegisteredAt() {
		return registeredAt;
	}

	public void setRegisteredAt(LocalDate registeredAt) {
		this.registeredAt = registeredAt;
	}
}
