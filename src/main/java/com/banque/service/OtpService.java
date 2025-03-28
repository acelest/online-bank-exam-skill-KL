package com.banque.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpService {

    private final Map<String, OtpData> otpMap = new ConcurrentHashMap<>();
    private static final int OTP_VALIDITY_MINUTES = 5;

    /**
     * Génère un code OTP pour un email donné
     * 
     * @param email l'adresse email pour laquelle générer le code OTP
     * @param action description de l'action sécurisée
     * @return le code OTP généré
     */
    public String generateOtp(String email, String action) {
        // Générer un code OTP à 6 chiffres
        String otpCode = generateOtp();
        
        // Calculer la date d'expiration (maintenant + 5 minutes)
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(OTP_VALIDITY_MINUTES);
        
        // Stocker l'OTP avec sa date d'expiration pour l'email donné
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
        // Vérifier si l'email a un OTP associé
        OtpData otpData = otpMap.get(email);
        if (otpData == null) {
            log.warn("Tentative de validation d'un OTP pour un email sans OTP: {}", email);
            return false;
        }
        
        // Vérifier si l'OTP est expiré
        if (LocalDateTime.now().isAfter(otpData.getExpiryTime())) {
            otpMap.remove(email);
            log.warn("OTP expiré pour l'email: {}", email);
            return false;
        }
        
        // Vérifier si le code OTP est correct
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
        // Ici, nous simulons l'envoi d'email pour le code OTP
        // Dans une implémentation réelle, vous utiliseriez un service d'email
        log.info("Simulation d'envoi d'email OTP à {} pour l'action {}: {}", email, action, otpCode);
    }
    
    @Data
    @AllArgsConstructor
    private static class OtpData {
        private final String otpCode;
        private final LocalDateTime expiryTime;
    }
}
