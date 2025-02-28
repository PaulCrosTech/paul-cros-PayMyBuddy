package com.openclassrooms.PayMyBuddy.dto;

import com.openclassrooms.PayMyBuddy.entity.DBUser;
import lombok.Data;

import java.util.List;

/**
 * DTO for the transfer page.
 */
@Data
public class TransferDto {

    private List<DBUser> connections;
    private String description;
    private double amount;

    List<TransactionWithDebitCreditDto> transactions;
}