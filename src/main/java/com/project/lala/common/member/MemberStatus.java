package com.project.lala.common.member;

/**
 * 이메일 인증: UNEMAILAUTH
 * 이메일 미인증: EMAILAUTH
 * 본인 인증: AUTH
 * 탈퇴 회원: DELETED
 *
 */
public enum MemberStatus {
	UNEMAILAUTH, EMAILAUTH, AUTH, DELETED
}