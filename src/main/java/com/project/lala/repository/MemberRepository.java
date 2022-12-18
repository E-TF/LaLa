package com.project.lala.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.lala.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findById(long id);

	Optional<Member> findByEmail(String email);

	Optional<Member> findByLoginId(String loginId);
}

