package com.banque.service;

import com.banque.model.User;

/**
 * Service responsable de la gestion des One-Time Passwords (OTP)
 */
public interface OTPService {
    
    /**
     * Génère un nouveau code OTP pour un utilisateur
     * 
     * @param user l'utilisateur pour lequel générer le code
     * @return le code OTP généré
     */
    String genererOTP(User user);
    
    /**
     * Valide un code OTP pour un utilisateur donné
     * 
     * @param username le nom d'utilisateur
     * @param otpCode le code OTP à valider
     * @return true si le code est valide, false sinon
     */
    boolean validerOTP(String username, String otpCode);
    
    /**
     * Envoie un code OTP par email à l'utilisateur
     * 
     * @param user l'utilisateur destinataire
     * @param otpCode le code OTP à envoyer
     */
    void envoyerOTPParEmail(User user, String otpCode);
}
