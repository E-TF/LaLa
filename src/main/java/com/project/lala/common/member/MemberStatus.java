package com.project.lala.common.member;

/**
 * 이메일 인증: UN_EMAIL_AUTH
 * 이메일 미인증: EMAIL_AUTH
 * 본인 인증: AUTH
 * 탈퇴 회원: DELETED
 */
public enum MemberStatus {
	UN_EMAIL_AUTH, EMAIL_AUTH, AUTH, DELETED
}

