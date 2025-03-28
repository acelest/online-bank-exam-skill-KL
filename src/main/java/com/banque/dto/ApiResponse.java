package com.banque.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe générique pour formater les réponses API
 * 
 * @param <T> le type de données retournées
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    
    private String statut;
    private String message;
    private T data;
}
