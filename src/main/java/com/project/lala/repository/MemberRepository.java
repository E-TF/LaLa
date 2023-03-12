package com.project.lala.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lala.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findById(long id);

	Optional<Member> findByEmail(String email);

	Optional<Member> findByLoginId(String loginId);
}
