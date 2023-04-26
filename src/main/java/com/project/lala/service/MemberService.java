package com.project.lala.service;

import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.lala.common.encrytion.EncryptionService;
import com.project.lala.common.encrytion.SHA512EncryptionService;
import com.project.lala.common.exception.EmailDuplicationException;
import com.project.lala.common.exception.ErrorCode;
import com.project.lala.common.exception.MemberDuplicationException;
import com.project.lala.dto.SignUpRequest;
import com.project.lala.dto.SignUpResponse;
import com.project.lala.entity.EmailAuth;
import com.project.lala.entity.Member;
import com.project.lala.repository.EmailAuthRepository;
import com.project.lala.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final EmailAuthRepository emailAuthRepository;

	private final EmailService emailService;
	EncryptionService encryptionService = new SHA512EncryptionService();

	@Transactional
	public SignUpResponse signUp(SignUpRequest requestDto) {
		validateDuplicateByLoginId(requestDto.getLoginId());
		validateDuplicateByEmail(requestDto.getEmail());

		EmailAuth emailAuth = emailAuthRepository.save(getBuild(requestDto));
		Member member = memberRepository.save(getEntity(requestDto));

		emailService.sendEmail(emailAuth.getEmail(), emailAuth.getAuthToken());

		return SignUpResponse.signUpResponse(member);
	}

	@Transactional
	public void updateMember(Long memberId, String password, String nickname) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new EntityNotFoundException("찾을 수 없는 회원입니다."));

		member.update(encryptionService.encrypt(password), nickname);

		memberRepository.save(member);
	}

	private Member getEntity(SignUpRequest requestDto) {
		return Member.builder()
			.loginId(requestDto.getLoginId())
			.password(encryptionService.encrypt(requestDto.getPassword()))
			.nickname(requestDto.getNickname())
			.name(requestDto.getName())
			.email(requestDto.getEmail())
			.build();
	}

	private EmailAuth getBuild(SignUpRequest requestDto) {
		return EmailAuth.builder()
			.email(requestDto.getEmail())
			.authToken(UUID.randomUUID().toString())
			.expired(false)
			.build();
	}

	private void validateDuplicateByLoginId(String loginId) {
		if (memberRepository.findByLoginId(loginId).isPresent()) {
			throw new MemberDuplicationException(ErrorCode.DUPLICATE_LOGIN_ID, "이미 존재하는 아이디 입니다.");
		}
	}

	private void validateDuplicateByEmail(String email) {
		if (memberRepository.findByEmail(email).isPresent()) {
			throw new EmailDuplicationException(ErrorCode.DUPLICATE_EMAIL, "이미 존재하는 이메일 입니다.");
		}
	}
}
