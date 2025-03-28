package com.banque.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpService {

    private final JavaMailSender emailSender;
    
    // Map pour stocker les OTPs (en production, utilisez une base de données)
    // La clé est l'email, la valeur contient le code OTP et sa date d'expiration
    private final Map<String, OtpData> otpMap = new ConcurrentHashMap<>();
    
    /**
     * Génère un code OTP à 6 chiffres et l'envoie par email
     * 
     * @param email l'adresse email à laquelle envoyer l'OTP
     * @param action description de l'action sécurisée 
     * @return le code OTP généré
     */
    public String generateAndSendOtp(String email, String action) {
        // Générer un code OTP à 6 chiffres
        String otpCode = generateOtp();
        
        // Définir la date d'expiration (5 minutes)
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5);
        
        // Stocker l'OTP avec sa date d'expiration
        otpMap.put(email, new OtpData(otpCode, expiryTime));
        
        // Envoyer l'OTP par email
        sendOtpByEmail(email, otpCode, action);
        
        log.info("OTP généré et envoyé à {} pour l'action: {}", email, action);
        
        return otpCode;
    }
    
    /**
     * Vérifie la validité d'un code OTP pour un email donné
     * 
     * @param email l'adresse email associée au code OTP
     * @param otpCode le code OTP à vérifier
     * @return true si le code est valide et non expiré, false sinon
     */
    public boolean validateOtp(String email, String otpCode) {
        OtpData otpData = otpMap.get(email);
        
        if (otpData == null) {
            log.warn("Tentative de validation d'un OTP pour un email sans OTP: {}", email);
            return false;
        }
        
        if (LocalDateTime.now().isAfter(otpData.getExpiryTime())) {
            // Supprimer l'OTP expiré
            otpMap.remove(email);
            log.warn("OTP expiré pour l'email: {}", email);
            return false;
        }
        
        if (otpData.getOtpCode().equals(otpCode)) {
            // Supprimer l'OTP après une validation réussie
            otpMap.remove(email);
            log.info("OTP validé avec succès pour l'email: {}", email);
            return true;
        }
        
        log.warn("OTP invalide pour l'email: {}", email);
        return false;
    }
    
    /**
     * Génère un code OTP à 6 chiffres
     * 
     * @return le code OTP généré
     */
    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Génère un nombre entre 100000 et 999999
        return String.valueOf(otp);
    }
    
    /**
     * Envoie un code OTP par email
     * 
     * @param email l'adresse email destinataire
     * @param otpCode le code OTP à envoyer
     * @param action description de l'action sécurisée
     */
    private void sendOtpByEmail(String email, String otpCode, String action) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Code de sécurité pour " + action);
        message.setText("Votre code de sécurité pour " + action + " est: " + otpCode + 
                       "\n\nCe code est valable pendant 5 minutes.");
        
        emailSender.send(message);
    }
    
    @Data
    private static class OtpData {
        private final String otpCode;
        private final LocalDateTime expiryTime;
    }
}
