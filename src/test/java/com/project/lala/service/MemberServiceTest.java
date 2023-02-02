package com.project.lala.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.project.lala.common.encrytion.EncryptionService;
import com.project.lala.dto.SignUpRequest;
import com.project.lala.entity.Member;
import com.project.lala.repository.MemberRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberServiceTest {

	private Member member;

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberRepository memberRepository;
	private SignUpRequest signUpRequest;
	@Autowired
	private EncryptionService encryptionService;

	@Test
	@Transactional
	@Rollback(false)
	public void signUp() {
		Member joinMember = Member.createMember("test_id", encryptionService.encrypt("!@#test123"),
			"test_nick", "test_name", "test_email@email.test");

		Member signupMember = memberRepository.save(joinMember);
		Optional<Member> findMemberId = memberRepository.findById(joinMember.getId());

		assertThat(findMemberId.get().getId()).isEqualTo(joinMember.getId());
		assertThat(findMemberId.get().getEmail()).isEqualTo(joinMember.getEmail());
	}

	@Test
	@DisplayName("이미 같은 ID가 존재하는 경우 가입 실패")
	public void dupIdExistsFail() {
		Member savedResult = memberRepository.save(testBuilderId());

		Optional<Member> findMemberId = memberRepository.findById(savedResult.getId());

		assertThat(findMemberId).isNotNull();
		assertThat(findMemberId.get().getId()).isNotNull();
		assertThat(findMemberId.get().getId()).isEqualTo(savedResult.getId());
	}

	@Test
	@DisplayName("중복된 아이디가 없는 경우 가입 성공")
	public void dupIdExistsSuccess() {
		memberRepository.save(testBuilderId());

		Optional<Member> savedMember = memberRepository.findById(1L);
		assertEquals(1L, savedMember.get().getId());
		assertEquals("test_email@email.test", savedMember.get().getEmail());
	}

	@Test
	@DisplayName("가입 후 메일전송")
	public void signUpThenSendMail() {
		memberRepository.save(testBuilderId());
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

	}

	private Member testBuilderId() {
		return Member.builder()
			.id(1L)
			.loginId("test_id")
			.email("test_email@email.test")
			.name("test_name")
			.password(encryptionService.encrypt("!@#test123"))
			.build();
	}

}
