package com.project.lala.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lala.entity.EmailAuth;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long>, EmailAuthCustomRepository {
}
