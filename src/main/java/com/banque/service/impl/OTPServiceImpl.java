package com.banque.service.impl;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.banque.model.User;
import com.banque.service.EmailService;
import com.banque.service.OTPService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OTPServiceImpl implements OTPService {

    private final EmailService emailService;
    
    // Structure pour stocker les OTP: Map<username, Map<otpCode, otpData>>
    private final Map<String, Map<String, OtpData>> otpStorage = new ConcurrentHashMap<>();
    
    // Durée de validité de l'OTP en minutes
    private static final int OTP_VALIDITY_MINUTES = 5;
    
    @Override
    public String genererOTP(User user) {
        log.info("Génération d'un code OTP pour l'utilisateur: {}", user.getUsername());
        
        // Générer un code OTP à 6 chiffres aléatoire
        String otpCode = genererCodeOTP();
        
        // Calculer la date d'expiration
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(OTP_VALIDITY_MINUTES);
        
        // Stocker l'OTP
        otpStorage.computeIfAbsent(user.getUsername(), k -> new ConcurrentHashMap<>())
                 .put(otpCode, new OtpData(otpCode, expirationTime));
        
        log.info("Code OTP généré pour l'utilisateur: {}", user.getUsername());
        
        return otpCode;
    }
    
    @Override
    public boolean validerOTP(String username, String otpCode) {
        log.info("Validation du code OTP pour l'utilisateur: {}", username);
        
        // Vérifier si l'utilisateur a des OTP stockés
        Map<String, OtpData> userOtps = otpStorage.get(username);
        if (userOtps == null) {
            log.error("Aucun OTP trouvé pour l'utilisateur: {}", username);
            return false;
        }
        
        // Vérifier si le code OTP existe
        OtpData otpData = userOtps.get(otpCode);
        if (otpData == null) {
            log.error("Code OTP incorrect pour l'utilisateur: {}", username);
            return false;
        }
        
        // Vérifier si l'OTP est expiré
        if (otpData.isExpired()) {
            log.error("Code OTP expiré pour l'utilisateur: {}", username);
            userOtps.remove(otpCode);
            return false;
        }
        
        // OTP valide, le supprimer pour ne pas le réutiliser
        userOtps.remove(otpCode);
        log.info("Code OTP validé avec succès pour l'utilisateur: {}", username);
        
        return true;
    }
    
    @Override
    public void envoyerOTPParEmail(User user, String otpCode) {
        log.info("Envoi du code OTP par email à l'utilisateur: {}", user.getUsername());
        
        // Préparer le contenu de l'email
        String sujet = "Votre code de sécurité - Banque en ligne";
        
        String contenu = String.format(
                "Bonjour %s,\n\n" +
                "Voici votre code de sécurité à usage unique: %s\n\n" +
                "Ce code est valable pendant %d minutes.\n\n" +
                "Si vous n'avez pas demandé ce code, veuillez ignorer cet email.\n\n" +
                "Cordialement,\n" +
                "L'équipe de la Banque en ligne",
                user.getUsername(), otpCode, OTP_VALIDITY_MINUTES);
        
        // Envoyer l'email
        emailService.envoyerEmail(user.getEmail(), sujet, contenu);
    }
    
    /**
     * Génère un code OTP à 6 chiffres aléatoire
     * @return le code OTP généré
     */
    private String genererCodeOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Génère un nombre entre 100000 et 999999
        return String.valueOf(otp);
    }
    
    @Data
    @AllArgsConstructor
    private static class OtpData {
        private final String otpCode;
        private final LocalDateTime expiryTime;
        
        /**
         * Vérifie si cet OTP est expiré
         * @return true si l'OTP est expiré, false sinon
         */
        public boolean isExpired() {
            return LocalDateTime.now().isAfter(expiryTime);
        }
    }
}
