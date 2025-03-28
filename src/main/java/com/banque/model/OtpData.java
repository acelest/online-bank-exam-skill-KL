package com.banque.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Classe pour stocker les données d'un OTP
 */
@Data
@RequiredArgsConstructor
public class OtpData {
    private final String otpCode;
    private final LocalDateTime expirationTime;
    
    /**
     * Vérifie si l'OTP est expiré
     * 
     * @return true si l'OTP est expiré, false sinon
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expirationTime);
    }
}
