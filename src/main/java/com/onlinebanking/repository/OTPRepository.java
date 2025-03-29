package com.onlinebanking.repository;

import com.onlinebanking.model.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Long> {
    Optional<OTP> findByEmailAndCodeAndPurposeAndUsedFalseAndExpiresAtAfter(
        String email, String code, String purpose, LocalDateTime now);

    boolean existsByEmailAndPurposeAndUsedFalseAndExpiresAtAfter(
        String email, String purpose, LocalDateTime now);

    Optional<OTP> findFirstByEmailAndPurposeOrderByCreatedAtDesc(String email, String purpose);
    Optional<OTP> findByEmailAndPurposeAndUsedFalse(String email, String purpose);
    void deleteByEmailAndPurposeAndUsedFalse(String email, String purpose);
} 