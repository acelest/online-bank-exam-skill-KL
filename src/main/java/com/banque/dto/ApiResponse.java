package com.banque.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe pour formater les réponses API de manière uniforme
 * @param <T> Type de données retournées
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String statut;
    private String message;
    private T data;
}
