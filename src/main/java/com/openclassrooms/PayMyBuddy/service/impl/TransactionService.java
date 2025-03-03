package com.openclassrooms.PayMyBuddy.service.impl;

import com.openclassrooms.PayMyBuddy.dto.TransactionDto;
import com.openclassrooms.PayMyBuddy.entity.DBUser;
import com.openclassrooms.PayMyBuddy.entity.Transaction;
import com.openclassrooms.PayMyBuddy.dto.TransactionWithDebitCreditDto;
import com.openclassrooms.PayMyBuddy.exceptions.TransactionInsufficientBalanceException;
import com.openclassrooms.PayMyBuddy.exceptions.UserNotFoundException;
import com.openclassrooms.PayMyBuddy.mapper.TransactionMapper;
import com.openclassrooms.PayMyBuddy.repository.DBUserRepository;
import com.openclassrooms.PayMyBuddy.repository.TransactionRepository;
import com.openclassrooms.PayMyBuddy.service.ITransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * The class Transaction service.
 */
@Slf4j
@Service
public class TransactionService implements ITransactionService {

    private final TransactionRepository transactionRepository;
    private final DBUserRepository userRepository;
    private final TransactionMapper transactionMapper;

    public TransactionService(TransactionRepository transactionRepository,
                              DBUserRepository userRepository,
                              TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.transactionMapper = transactionMapper;
    }


    /**
     * Find Transactions (as sender or receiver) by email, last transactions first
     *
     * @param email    the email
     * @param pageable the pageable
     * @return the pageable list of TransactionWithDebitCreditDto
     * @throws UserNotFoundException the user not found exception
     */
    @Override
    public Page<TransactionWithDebitCreditDto> findAsSendOrReceiverByEmail(String email, Pageable pageable) throws UserNotFoundException {

        DBUser user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UserNotFoundException("Utilisateur non trouvé avec l'email : " + email)
                );
        Page<Transaction> transactions = transactionRepository.findBySender_UserIdOrReceiver_UserId_OrderByTransactionIdDesc(user.getUserId(), user.getUserId(), pageable);

        List<TransactionWithDebitCreditDto> dto = transactions.stream()
                .map(transaction -> transactionMapper.toTransactionWithDebitCredit(user.getUserId(), transaction))
                .toList();

        return new PageImpl<>(dto, pageable, transactions.getTotalElements());
    }


    /**
     * Save transaction
     *
     * @param debtorEmail    the debtor email
     * @param transactionDto the transfer dto
     * @throws UserNotFoundException                   the user not found exception
     * @throws TransactionInsufficientBalanceException the transaction insufficient balance exception
     */
    @Override
    @Transactional
    public void save(String debtorEmail, TransactionDto transactionDto) throws UserNotFoundException, TransactionInsufficientBalanceException {

        log.info("====> Save transaction <====");
        DBUser debtorUser = userRepository.findByEmail(debtorEmail)
                .orElseThrow(
                        () -> new UserNotFoundException("Utilisateur non trouvé avec l'email : " + debtorEmail)
                );

        DBUser creditorUser = userRepository.findByUserId(transactionDto.getUserId())
                .orElseThrow(
                        () -> new UserNotFoundException("Utilisateur créditeur non trouvé")
                );

        if (debtorUser.getSolde() < transactionDto.getAmount()) {
            throw new TransactionInsufficientBalanceException(debtorUser.getUserName(), debtorUser.getSolde(), transactionDto.getAmount());
        }

        debtorUser.setSolde(debtorUser.getSolde() - transactionDto.getAmount());

        creditorUser.setSolde(creditorUser.getSolde() + transactionDto.getAmount());

        Transaction transaction = new Transaction();
        transaction.setDescription(transactionDto.getDescription());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setSender(debtorUser);
        transaction.setReceiver(creditorUser);
        transactionRepository.save(transaction);
    }


}
