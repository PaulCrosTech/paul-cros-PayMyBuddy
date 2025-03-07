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

    /**
     * Constructor
     *
     * @param transactionRepository the transaction repository
     * @param userRepository        the user repository
     * @param transactionMapper     the transaction mapper
     */
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
        log.debug("====> Find transactions of user '{}' as sender or receiver <====", email);

        DBUser user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UserNotFoundException("Utilisateur non trouvé avec l'email : " + email)
                );
        int userId = user.getUserId();
        Page<Transaction> transactions = transactionRepository.findBySender_UserIdOrReceiver_UserId_OrderByTransactionIdDesc(userId, userId, pageable);

        List<TransactionWithDebitCreditDto> dto = transactions.stream()
                .map(transaction -> transactionMapper.toTransactionWithDebitCredit(userId, transaction))
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

        log.debug("====> Creating new transaction <====");
        DBUser debtorUser = userRepository.findByEmail(debtorEmail)
                .orElseThrow(
                        () -> new UserNotFoundException("Utilisateur non trouvé avec l'email : " + debtorEmail)
                );
        log.debug("====> Debtor '{}' found <====", debtorUser.getEmail());

        DBUser creditorUser = userRepository.findByUserName(transactionDto.getUserName())
                .orElseThrow(
                        () -> new UserNotFoundException("Utilisateur créditeur '" + transactionDto.getUserName() + "' non trouvé")
                );
        log.debug("====> Creditor '{}' found <====", creditorUser.getEmail());
        
        if (debtorUser.getSolde() < transactionDto.getAmount()) {
            throw new TransactionInsufficientBalanceException(debtorUser.getUserName(), debtorUser.getSolde(), transactionDto.getAmount());
        }

        log.debug("====> The debtor has enough funds ({}) to complete the transaction ({}). <====", debtorUser.getSolde(), transactionDto.getAmount());

        debtorUser.setSolde(debtorUser.getSolde() - transactionDto.getAmount());

        creditorUser.setSolde(creditorUser.getSolde() + transactionDto.getAmount());

        Transaction transaction = new Transaction();
        transaction.setDescription(transactionDto.getDescription());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setSender(debtorUser);
        transaction.setReceiver(creditorUser);
        transactionRepository.save(transaction);
        log.debug("====> Transaction created <====");
    }


}
