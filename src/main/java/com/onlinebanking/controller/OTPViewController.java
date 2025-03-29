package com.onlinebanking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OTPViewController {

    @GetMapping("/verify-otp")
    public String showOTPVerification(
            @RequestParam String email,
            @RequestParam String purpose,
            Model model) {
        model.addAttribute("email", email);
        model.addAttribute("purpose", purpose);
        return "otp-verification";
    }
} 