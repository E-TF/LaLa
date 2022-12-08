package com.project.lala.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.lala.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	Member findById(long id);

	Member findByEmail(String email);

	Member findByLoginId(String loginId);
}