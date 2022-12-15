package com.project.lala.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EMAIL_AUTH")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailAuth {
	private static final long EMAIL_TOKEN_EXPIRATION_TIME_VALUE = 5L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;

	@Column(name = "auth_token")
	private String authToken;

	@Column
	private boolean expired;

	@Column(name = "expired_at")
	private LocalDateTime expiredAt;

	@Builder
	public EmailAuth(String email, String authToken, Boolean expired) {
		this.email = email;
		this.authToken = authToken;
		this.expired = expired;
		this.expiredAt = LocalDateTime.now().plusMinutes(EMAIL_TOKEN_EXPIRATION_TIME_VALUE);
	}

	public void useToken() {
		expired = true;
	}
}
