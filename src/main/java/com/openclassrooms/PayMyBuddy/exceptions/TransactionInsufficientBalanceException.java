package com.openclassrooms.PayMyBuddy.exceptions;

import lombok.extern.slf4j.Slf4j;


/**
 * UserWithSameUserNameExistsException Class
 */
@Slf4j
public class TransactionInsufficientBalanceException extends RuntimeException {
    /**
     * Constructor
     *
     * @param debtorUserName debtor user name
     * @param debtorBalance  debtor balance
     * @param amount         amount of the transaction
     */
    public TransactionInsufficientBalanceException(String debtorUserName, double debtorBalance, double amount) {
        super("Votre solde (" + debtorBalance + "€) est insuffisant pour effectuer une transaction de " + amount + "€.");
        log.error("====> <exception> TransactionInsufficientBalanceException : débiteur {} solde {} montant transaction {} <====", debtorUserName, debtorBalance, amount);
    }
}
