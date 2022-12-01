package com.project.lala.service;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.lala.dto.SignUpRequestDto;
import com.project.lala.entity.Member;
import com.project.lala.repository.MemberRepository;

@SpringBootTest
class MemberServiceTest {

	private Member member;
	private MemberService memberService;
	private MemberRepository memberRepository;
	private SignUpRequestDto signUpRequestDto;

	@BeforeEach
	void beforeEach() {
		member = Member.createMember("test_id", "!@#test123",
			"test_nick", "test_name",
			"test_email@email.test", LocalDate.now());
	}

	@Test
	public void 회원가입() throws Exception {

	}
}