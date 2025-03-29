package com.onlinebanking.model;

import lombok.Data;

@Data
public class TransferRequest {
    private String email;
    private String fromAccount;
    private String toAccount;
    private String recipientId;
    private String accountType;
    private double amount;
} 