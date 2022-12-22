package com.project.lala.service;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.project.lala.common.encrytion.EncryptionService;
import com.project.lala.dto.SignUpRequest;
import com.project.lala.entity.Member;
import com.project.lala.repository.MemberRepository;

@SpringBootTest
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
}
