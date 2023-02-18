package com.project.lala.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.project.lala.common.encrytion.EncryptionService;
import com.project.lala.common.encrytion.SHA512EncryptionService;
import com.project.lala.entity.Member;
import com.project.lala.repository.MemberRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberServiceTest {

	@Autowired
	private MemberRepository memberRepository;
	private final EncryptionService encryptionService = new SHA512EncryptionService();

	@Test
	@Transactional
	@Rollback(false)
	void signUp() {
		Member joinMember = Member.createMember("test_id", encryptionService.encrypt("!@#test123"),
			"test_nick", "test_name", "test_email@email.test");

		Member signupMember = memberRepository.save(joinMember);
		Optional<Member> findMemberId = memberRepository.findById(joinMember.getId());

		assertThat(findMemberId.get().getId()).isEqualTo(joinMember.getId());
		assertThat(findMemberId.get().getEmail()).isEqualTo(joinMember.getEmail());
	}

	@Test
	@DisplayName("이미 같은 ID가 존재하는 경우 가입 실패")
	void dupIdExistsFail() {
		Member savedResult = memberRepository.save(getMember());

		Optional<Member> findMemberId = memberRepository.findById(savedResult.getId());

		assertThat(findMemberId).isNotNull();
		assertThat(findMemberId.get().getId()).isNotNull();
		assertThat(findMemberId.get().getId()).isEqualTo(savedResult.getId());
	}

	@Test
	@DisplayName("중복된 아이디가 없는 경우 가입 성공")
	void dupIdExistsSuccess() {
		memberRepository.save(getMember());

		Optional<Member> savedMember = memberRepository.findById(1L);
		assertEquals(1L, savedMember.get().getId());
		assertEquals("test_email@email.test", savedMember.get().getEmail());
	}

	private Member getMember() {
		return Member.builder()
			.loginId("test_id")
			.email("test_email@email.test")
			.name("test_name")
			.password(encryptionService.encrypt("!@#test123"))
			.build();
	}

}
