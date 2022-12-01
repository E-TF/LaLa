package com.project.lala.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.lala.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	List<Member> findById(String loginId);

	List<Member> findByEmail(String email);

}
