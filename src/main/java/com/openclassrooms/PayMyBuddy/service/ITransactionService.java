package com.openclassrooms.PayMyBuddy.service;


import com.openclassrooms.PayMyBuddy.dto.TransactionWithDebitCreditDto;
import com.openclassrooms.PayMyBuddy.dto.TransactionDto;
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
     * @param email    the email
     * @param pageable the pageable
     * @return the page
     * @throws UserNotFoundException the user not found exception
     */
    Page<TransactionWithDebitCreditDto> findAsSendOrReceiverByEmail(String email, Pageable pageable) throws UserNotFoundException;

    /**
     * Save transaction
     *
     * @param DebtorEmail    the debtor email
     * @param transactionDto the transfer dto
     * @throws UserNotFoundException                   the user not found exception
     * @throws TransactionInsufficientBalanceException the transaction insufficient balance exception
     */
    void save(String DebtorEmail, TransactionDto transactionDto) throws UserNotFoundException, TransactionInsufficientBalanceException;
}
