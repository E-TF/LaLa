package com.project.lala.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
	private static final long EMAIL_TOKEN_EXPIRATION_TIME_MINUTE_VALUE = 5;

	@Id
	@GeneratedValue
	private Long id;

	private String email;

	private String authToken;

	private boolean expired;

	private LocalDateTime expiredAt;

	@Builder
	public EmailAuth(String email, String authToken, Boolean expired) {
		this.email = email;
		this.authToken = authToken;
		this.expired = expired;
		this.expiredAt = LocalDateTime.now().plusMinutes(EMAIL_TOKEN_EXPIRATION_TIME_MINUTE_VALUE);
	}

	public void useEmailAuthToken() {
		this.expired = true;
	}

	public void expiredToken() {
		this.expired = true;
	}

}
