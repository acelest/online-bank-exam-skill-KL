package com.banque.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.banque.service.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    
    @Override
    public void envoyerEmail(String destinataire, String sujet, String contenu) {
        log.info("Envoi d'un email à: {}", destinataire);
        
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(destinataire);
            message.setSubject(sujet);
            message.setText(contenu);
            
            mailSender.send(message);
            
            log.info("Email envoyé avec succès à: {}", destinataire);
        } catch (Exception e) {
            log.error("Erreur lors de l'envoi de l'email à {}: {}", destinataire, e.getMessage());
        }
    }
}
