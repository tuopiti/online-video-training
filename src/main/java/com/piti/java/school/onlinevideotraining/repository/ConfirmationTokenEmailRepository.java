package com.piti.java.school.onlinevideotraining.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.piti.java.school.onlinevideotraining.model.ConfirmationTokenEmail;

import jakarta.transaction.Transactional;

public interface ConfirmationTokenEmailRepository extends JpaRepository<ConfirmationTokenEmail, Long>{
	Optional<ConfirmationTokenEmail> findByToken(String token);
	
	@Transactional
    @Modifying
    @Query(value ="UPDATE confirmation_token_emails SET confirmed_at = now() WHERE token = :token", nativeQuery=true)
    int updateConfirmedAt(@Param("token") String token);
}
