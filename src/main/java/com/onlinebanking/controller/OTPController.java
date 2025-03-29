package com.onlinebanking.controller;

import com.onlinebanking.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/otp")
public class OTPController {

    @Autowired
    private OTPService otpService;

    @PostMapping("/generate")
    public ResponseEntity<?> generateOTP(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String purpose = request.get("purpose");

        if (email == null || purpose == null) {
            return ResponseEntity.badRequest().body("Email et purpose sont requis");
        }

        try {
            otpService.generateOTP(email, purpose);
            return ResponseEntity.ok().body("OTP envoyé avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'envoi de l'OTP");
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyOTP(
            @RequestParam String email,
            @RequestParam String code,
            @RequestParam String purpose) {
        try {
            boolean isValid = otpService.verifyOTP(email, code, purpose);
            if (isValid) {
                return ResponseEntity.ok("OTP verified successfully");
            }
            return ResponseEntity.badRequest().body("Invalid OTP");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error verifying OTP: " + e.getMessage());
        }
    }
} 