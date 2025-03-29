package com.onlinebanking.controller;

import com.onlinebanking.service.TransferService;
import com.onlinebanking.service.OTPService;
import com.onlinebanking.model.TransferRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfer")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @Autowired
    private OTPService otpService;

    @PostMapping("/internal")
    public ResponseEntity<?> transferBetweenAccounts(@RequestBody TransferRequest request) {
        try {
            // Vérifier que l'OTP a été validé
            if (!otpService.isOTPValid(request.getEmail(), "TRANSFER")) {
                return ResponseEntity.badRequest().body("Code OTP non validé");
            }

            // Effectuer le transfert
            transferService.transferBetweenAccounts(
                request.getFromAccount(),
                request.getToAccount(),
                request.getAmount()
            );

            return ResponseEntity.ok().body("Transfert effectué avec succès");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/external")
    public ResponseEntity<?> transferToRecipient(@RequestBody TransferRequest request) {
        try {
            // Vérifier que l'OTP a été validé
            if (!otpService.isOTPValid(request.getEmail(), "TRANSFER")) {
                return ResponseEntity.badRequest().body("Code OTP non validé");
            }

            // Effectuer le transfert
            transferService.transferToRecipient(
                request.getRecipientId(),
                request.getAccountType(),
                request.getAmount()
            );

            return ResponseEntity.ok().body("Transfert effectué avec succès");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 