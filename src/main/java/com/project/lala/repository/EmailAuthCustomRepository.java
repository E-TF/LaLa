package com.project.lala.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import com.project.lala.entity.EmailAuth;

public interface EmailAuthCustomRepository {
	Optional<EmailAuth> findValidAuthByEmail(String email, String authToken, LocalDateTime currentTime);
}

