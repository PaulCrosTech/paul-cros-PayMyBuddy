package com.openclassrooms.PayMyBuddy.service;


import com.openclassrooms.PayMyBuddy.dto.TransactionWithDebitCreditDto;
import com.openclassrooms.PayMyBuddy.dto.TransferDto;
import com.openclassrooms.PayMyBuddy.exceptions.TransactionInsufficientBalanceException;
import com.openclassrooms.PayMyBuddy.exceptions.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * The interface Transaction service.
 */
public interface ITransactionService {

    /**
     * Find Transactions (as sender or receiver) by email, last transactions first
     *
     * @param email the email
     * @return the pageable list of TransactionWithDebitCreditDto
     */
    Page<TransactionWithDebitCreditDto> findAsSendOrReceiverByEmail(String email, Pageable pageable) throws UserNotFoundException;

    /**
     * Save transaction
     *
     * @param DebtorEmail the debtor email
     * @param transferDto the transfer dto
     * @throws UserNotFoundException                   the user not found exception
     * @throws TransactionInsufficientBalanceException the transaction insufficient balance exception
     */
    void save(String DebtorEmail, TransferDto transferDto) throws UserNotFoundException, TransactionInsufficientBalanceException;
}
