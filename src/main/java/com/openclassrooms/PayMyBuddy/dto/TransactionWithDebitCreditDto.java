package com.openclassrooms.PayMyBuddy.dto;

import lombok.Data;

/**
 * DTO for transaction with debit or credit
 */
@Data
public class TransactionWithDebitCreditDto {

    private String description;
    private String relationUserName;
    private double amount;
    private boolean debit;

}
