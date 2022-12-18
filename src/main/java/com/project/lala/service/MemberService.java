package com.project.lala.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.lala.common.encrytion.EncryptionService;
import com.project.lala.dto.SignUpRequestDto;
import com.project.lala.dto.SignUpResponseDto;
import com.project.lala.entity.EmailAuth;
import com.project.lala.entity.Member;
import com.project.lala.repository.EmailAuthRepository;
import com.project.lala.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

	private final MemberRepository memberRepository;
	private final EmailAuthRepository emailAuthRepository;

	private final EmailService emailService;
	private final EncryptionService encryptionService;

	@Transactional
	public SignUpResponseDto signUp(SignUpRequestDto requestDto) {
		validateDuplicateByLoginId(requestDto.getLoginId());
		validateDuplicateByEmail(requestDto.getEmail());

		EmailAuth emailAuth = emailAuthRepository.save(
			EmailAuth.builder()
				.email(requestDto.getEmail())
				.authToken(UUID.randomUUID().toString())
				.expired(false)
				.build());

		Member member = memberRepository.save(
			Member.builder()
				.loginId(requestDto.getLoginId())
				.password(encryptionService.encrypt(requestDto.getPassword()))
				.nickname(requestDto.getNickname())
				.name(requestDto.getName())
				.email(requestDto.getEmail())
				.build());

		emailService.sendEmail(emailAuth.getEmail(), emailAuth.getAuthToken());

		return SignUpResponseDto.builder()
			.id(member.getId())
			.loginId(member.getLoginId())
			.password(member.getPassword())
			.nickname(member.getNickname())
			.name(member.getName())
			.email(member.getEmail())
			.build();
	}

	@Transactional
	public void confirmEmail(SignUpRequestDto signUpRequestDto) {
		EmailAuth emailAuth = emailAuthRepository.findValidAuthByEmail(signUpRequestDto.getEmail(),
			signUpRequestDto.getAuthToken(), LocalDateTime.now()).orElseThrow(IllegalArgumentException::new);
		Member member = memberRepository.findByEmail(signUpRequestDto.getEmail())
			.orElseThrow(IllegalArgumentException::new);
		emailAuth.useToken();
	}

	private void validateDuplicateByLoginId(String loginId) {
		if (memberRepository.findByLoginId(loginId).isPresent()) {
			throw new IllegalStateException("이미 존재하는 아이디 입니다.");
		}
	}

	private void validateDuplicateByEmail(String email) {
		if (memberRepository.findByEmail(email).isPresent()) {
			throw new IllegalStateException("이미 존재하는 이메일 입니다.");
		}
	}
}
