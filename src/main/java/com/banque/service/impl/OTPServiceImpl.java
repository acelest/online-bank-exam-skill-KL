package com.banque.service.impl;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.banque.model.User;
import com.banque.service.EmailService;
import com.banque.service.OTPService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OTPServiceImpl implements OTPService {

    private static final Logger log = LoggerFactory.getLogger(OTPServiceImpl.class);
    private final EmailService emailService;
    
    // Structure pour stocker les OTP: Map<username, Map<otpCode, expirationTime>>
    private final Map<String, Map<String, LocalDateTime>> otpStorage = new ConcurrentHashMap<>();
    
    // Durée de validité de l'OTP en minutes
    private static final int OTP_VALIDITY_MINUTES = 5;
    
    @Override
    public String genererOTP(User user) {
        log.info("Génération d'un code OTP pour l'utilisateur: {}", user.getUsername());
        
        // Générer un code OTP à 6 chiffres
        String otpCode = genererCodeOTP();
        
        // Définir le temps d'expiration
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(OTP_VALIDITY_MINUTES);
        
        // Stocker l'OTP avec son temps d'expiration
        otpStorage.computeIfAbsent(user.getUsername(), k -> new ConcurrentHashMap<>())
                 .put(otpCode, expirationTime);
        
        log.info("Code OTP généré pour l'utilisateur: {}", user.getUsername());
        
        return otpCode;
    }
    
    @Override
    public boolean validerOTP(String username, String otpCode) {
        log.info("Validation du code OTP pour l'utilisateur: {}", username);
        
        // Vérifier si l'utilisateur a des OTP stockés
        Map<String, LocalDateTime> userOtps = otpStorage.get(username);
        if (userOtps == null) {
            log.error("Aucun OTP trouvé pour l'utilisateur: {}", username);
            return false;
        }
        
        // Vérifier si le code OTP existe et n'est pas expiré
        LocalDateTime expirationTime = userOtps.get(otpCode);
        if (expirationTime == null) {
            log.error("Code OTP incorrect pour l'utilisateur: {}", username);
            return false;
        }
        
        if (expirationTime.isBefore(LocalDateTime.now())) {
            log.error("Code OTP expiré pour l'utilisateur: {}", username);
            userOtps.remove(otpCode);
            return false;
        }
        
        // Le code OTP est valide, le supprimer pour éviter qu'il soit réutilisé
        userOtps.remove(otpCode);
        log.info("Code OTP validé avec succès pour l'utilisateur: {}", username);
        
        return true;
    }
    
    @Override
    public void envoyerOTPParEmail(User user, String otpCode) {
        log.info("Envoi du code OTP par email à l'utilisateur: {}", user.getUsername());
        
        String sujet = "Votre code de sécurité pour Banque en ligne";
        String contenu = String.format(
                "Bonjour %s,\n\n" +
                "Votre code de sécurité à usage unique est: %s\n\n" +
                "Ce code est valable pendant %d minutes.\n\n" +
                "Si vous n'avez pas demandé ce code, veuillez ignorer cet email.\n\n" +
                "Cordialement,\n" +
                "L'équipe Banque en ligne",
                user.getUsername(), otpCode, OTP_VALIDITY_MINUTES);
        
        emailService.envoyerEmail(user.getEmail(), sujet, contenu);
    }
    
    /**
     * Génère un code OTP à 6 chiffres aléatoire
     * @return le code OTP généré
     */
    private String genererCodeOTP() {
        SecureRandom random = new SecureRandom();
        int code = 100000 + random.nextInt(900000); // Génère un nombre entre 100000 et 999999
        return String.valueOf(code);
    }
}
