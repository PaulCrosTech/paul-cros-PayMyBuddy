package com.openclassrooms.PayMyBuddy.service;


import com.openclassrooms.PayMyBuddy.dto.TransactionWithDebitCreditDto;
import com.openclassrooms.PayMyBuddy.exceptions.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * The interface Transaction service.
 */
public interface ITransactionService {

    /**
     * Find Transactions (as sender or receiver) by email
     *
     * @param email the email
     * @return the pageable list of TransactionWithDebitCreditDto
     */
    Page<TransactionWithDebitCreditDto> findAsSendOrReceiverByEmail(String email, Pageable pageable) throws UserNotFoundException;
}
