package com.onlinebanking.service;

import com.onlinebanking.model.OTP;
import com.onlinebanking.repository.OTPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OTPService {

    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    private JavaMailSender mailSender;

    public void generateOTP(String email, String purpose) {
        // Générer un code OTP à 6 chiffres
        String code = generateOTPCode();

        // Créer et sauvegarder l'OTP
        OTP otp = new OTP();
        otp.setEmail(email);
        otp.setCode(code);
        otp.setPurpose(purpose);
        otp.setCreatedAt(LocalDateTime.now());
        otp.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        otp.setUsed(false);
        otpRepository.save(otp);

        // Envoyer l'email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Code de vérification");
        message.setText("Votre code de vérification est : " + code + "\nCe code expire dans 5 minutes.");
        mailSender.send(message);
    }

    public boolean verifyOTP(String email, String code, String purpose) {
        OTP otp = otpRepository.findByEmailAndCodeAndPurposeAndUsedFalseAndExpiresAtAfter(
            email, code, purpose, LocalDateTime.now())
            .orElse(null);

        if (otp != null) {
            otp.setUsed(true);
            otpRepository.save(otp);
            return true;
        }

        return false;
    }

    public boolean isOTPValid(String email, String purpose) {
        return otpRepository.existsByEmailAndPurposeAndUsedFalseAndExpiresAtAfter(
            email, purpose, LocalDateTime.now());
    }

    private String generateOTPCode() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
} 