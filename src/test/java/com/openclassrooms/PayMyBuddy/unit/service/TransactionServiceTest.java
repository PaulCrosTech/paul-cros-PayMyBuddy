package com.openclassrooms.PayMyBuddy.unit.service;

import com.openclassrooms.PayMyBuddy.dto.TransactionDto;
import com.openclassrooms.PayMyBuddy.dto.TransactionWithDebitCreditDto;
import com.openclassrooms.PayMyBuddy.entity.DBUser;
import com.openclassrooms.PayMyBuddy.entity.Transaction;
import com.openclassrooms.PayMyBuddy.exceptions.*;
import com.openclassrooms.PayMyBuddy.mapper.TransactionMapper;
import com.openclassrooms.PayMyBuddy.repository.DBUserRepository;
import com.openclassrooms.PayMyBuddy.repository.TransactionRepository;
import com.openclassrooms.PayMyBuddy.service.ITransactionService;
import com.openclassrooms.PayMyBuddy.service.impl.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {


    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private DBUserRepository userRepository;
    @Mock
    private TransactionMapper transactionMapper;


    private ITransactionService transactionService;

    @BeforeEach
    public void setUpPerTest() {
        transactionService = new TransactionService(transactionRepository, userRepository, transactionMapper);
    }

    /**
     * Testing method save
     * Given valid informations
     * When save
     * Then transaction is saved
     */
    @Test
    public void givenValidInformations_whenSave_thenTransactionIsSaved() {
        // Given
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setUserName("creditorUserName");
        transactionDto.setDescription("description");
        transactionDto.setAmount(100.0);

        DBUser debtor = new DBUser();
        debtor.setUserId(1);
        debtor.setEmail("debtor@mail.fr");
        debtor.setSolde(1000.0);

        DBUser creditor = new DBUser();
        creditor.setUserName(transactionDto.getUserName());
        creditor.setEmail("creditor@mail.fr");
        creditor.setSolde(0.0);

        when(userRepository.findByEmail(debtor.getEmail())).thenReturn(Optional.of(debtor));
        when(userRepository.findByUserName(transactionDto.getUserName())).thenReturn(Optional.of(creditor));

        // When
        transactionService.save(debtor.getEmail(), transactionDto);

        // Then
        assertEquals(900.0, debtor.getSolde());
        assertEquals(100.0, creditor.getSolde());

        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    /**
     * Testing method save
     * Given unknown debtor email
     * When save
     * Then throw UserNotFoundException
     */
    @Test
    public void givenUnknowDebtorEmail_whenSave_thenThrowUserNotFoundException() {
        // Given
        String debtorEmail = "unknow@mail.fr";
        when(userRepository.findByEmail(debtorEmail)).thenReturn(Optional.empty());

        // When && Then
        assertThrows(
                UserNotFoundException.class,
                () -> transactionService.save(debtorEmail, new TransactionDto())
        );
    }

    /**
     * Testing method save
     * Given unknow creditor user id
     * When save
     * Then throw UserNotFoundException
     */
    @Test
    public void givenUnknowCreditorUserId_whenSave_thenThrowUserNotFoundException() {
        // Given
        String debtorEmail = "mail@mail.fr";

        when(userRepository.findByEmail(debtorEmail)).thenReturn(Optional.of(new DBUser()));
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.empty());

        // When && Then
        assertThrows(
                UserNotFoundException.class,
                () -> transactionService.save(debtorEmail, new TransactionDto())
        );
    }

    /**
     * Testing method save
     * Given debtor with insufficient balance
     * When save
     * Then throw TransactionInsufficientBalanceException
     */
    @Test
    public void givenDebtorInsufficientBalance_whenSave_thenTransactionInsufficientBalanceException() {
        // Given
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setUserName("creditorUserName");
        transactionDto.setDescription("description");
        transactionDto.setAmount(100.0);

        DBUser debtor = new DBUser();
        debtor.setUserId(1);
        debtor.setUserName("debtorUserName");
        debtor.setEmail("debtor@mail.fr");
        debtor.setSolde(0.0);

        DBUser creditor = new DBUser();
        creditor.setUserName(transactionDto.getUserName());
        creditor.setEmail("creditor@mail.fr");
        creditor.setSolde(0.0);

        when(userRepository.findByEmail(debtor.getEmail())).thenReturn(Optional.of(debtor));
        when(userRepository.findByUserName(transactionDto.getUserName())).thenReturn(Optional.of(creditor));

        // When && Then
        assertThrows(
                TransactionInsufficientBalanceException.class,
                () -> transactionService.save(debtor.getEmail(), transactionDto)
        );

    }


    /**
     * Testing method findAsSendOrReceiverByEmail
     * Given valid email
     * When findAsSendOrReceiverByEmail
     * Then throw UserNotFoundException
     */
    @Test
    public void givenUnknowEmail_whenFindAsSendOrReceiverByEmail_thenThrowUserNotFoundException() {
        // Given
        String email = "unknow@mail.fr";

        // When && Then
        assertThrows(
                UserNotFoundException.class,
                () -> transactionService.findAsSendOrReceiverByEmail(email, any(Pageable.class))
        );
    }

    /**
     * Testing method findAsSendOrReceiverByEmail
     * Given valid informations
     * When findAsSendOrReceiverByEmail
     * Then return transactions
     */
    @Test
    public void givenValidInformations_whenFindAsSendOrReceiverByEmail_thenReturnTransactions() {
        // Given
        DBUser dbUser = new DBUser();
        dbUser.setUserId(1);
        dbUser.setEmail("test@example.com");

        Pageable pageable = PageRequest.of(0, 10);

        Transaction transaction = new Transaction();
        Page<Transaction> transactions = new PageImpl<>(List.of(transaction));

        TransactionWithDebitCreditDto dto = new TransactionWithDebitCreditDto();

        when(userRepository.findByEmail(dbUser.getEmail())).thenReturn(Optional.of(dbUser));
        when(transactionRepository.findBySender_UserIdOrReceiver_UserId_OrderByTransactionIdDesc(dbUser.getUserId(), dbUser.getUserId(), pageable))
                .thenReturn(transactions);
        when(transactionMapper.toTransactionWithDebitCredit(dbUser.getUserId(), transaction)).thenReturn(dto);

        // When
        Page<TransactionWithDebitCreditDto> result = transactionService.findAsSendOrReceiverByEmail(dbUser.getEmail(), pageable);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(dto, result.getContent().getFirst());

        verify(userRepository, times(1)).findByEmail(dbUser.getEmail());
        verify(transactionRepository, times(1))
                .findBySender_UserIdOrReceiver_UserId_OrderByTransactionIdDesc(dbUser.getUserId(), dbUser.getUserId(), pageable);
        verify(transactionMapper, times(1)).toTransactionWithDebitCredit(dbUser.getUserId(), transaction);
    }

}
