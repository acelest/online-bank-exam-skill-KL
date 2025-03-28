package com.banque.service;

/**
 * Service responsable de l'envoi d'emails
 */
public interface EmailService {
    
    /**
     * Envoie un email
     * 
     * @param destinataire l'adresse email du destinataire
     * @param sujet le sujet de l'email
     * @param contenu le contenu de l'email
     */
    void envoyerEmail(String destinataire, String sujet, String contenu);
}
