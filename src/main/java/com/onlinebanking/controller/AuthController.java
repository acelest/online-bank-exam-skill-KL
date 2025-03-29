package com.onlinebanking.controller;

import com.onlinebanking.service.AuthService;
import com.onlinebanking.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private OTPService otpService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            String username = credentials.get("username");
            String password = credentials.get("password");

            if (username == null || password == null) {
                return ResponseEntity.badRequest().body("Username et password sont requis");
            }

            // Première étape : vérification des identifiants
            String email = authService.validateCredentials(username, password);
            
            // Générer et envoyer l'OTP
            otpService.generateOTP(email, "LOGIN");
            
            return ResponseEntity.ok().body(Map.of("email", email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/complete-login")
    public ResponseEntity<?> completeLogin(
            @RequestParam String email,
            @RequestParam String code) {
        try {
            boolean isValid = otpService.verifyOTP(email, code, "LOGIN");
            if (isValid) {
                String token = authService.generateToken(email);
                return ResponseEntity.ok().body(Map.of("token", token));
            }
            return ResponseEntity.badRequest().body("Code OTP invalide");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 