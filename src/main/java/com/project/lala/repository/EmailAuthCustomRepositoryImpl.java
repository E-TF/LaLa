package com.project.lala.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.project.lala.entity.EmailAuth;
import com.project.lala.entity.QEmailAuth;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class EmailAuthCustomRepositoryImpl implements EmailAuthCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public EmailAuthCustomRepositoryImpl(EntityManager em) {
		this.jpaQueryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Optional<EmailAuth> findValidAuthByEmail(String email, String authToken, LocalDateTime currentTime) {
		log.info("{}, {}, {}", email, authToken, currentTime);
		EmailAuth emailAuth = jpaQueryFactory
			.selectFrom(QEmailAuth.emailAuth)
			.where(QEmailAuth.emailAuth.email.eq(email),
				QEmailAuth.emailAuth.authToken.eq(authToken),
				QEmailAuth.emailAuth.expiredAt.goe(currentTime),
				QEmailAuth.emailAuth.expired.eq(false))
			.fetchFirst();

		return Optional.ofNullable(emailAuth);
	}
}
