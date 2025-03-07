package com.openclassrooms.PayMyBuddy.unit.mapper;

import com.openclassrooms.PayMyBuddy.dto.TransactionWithDebitCreditDto;
import com.openclassrooms.PayMyBuddy.entity.DBUser;
import com.openclassrooms.PayMyBuddy.entity.Transaction;
import com.openclassrooms.PayMyBuddy.mapper.TransactionMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Transaction mapper test.
 */
@ExtendWith(MockitoExtension.class)
public class TransactionMapperTest {

    private static TransactionMapper transactionMapper;

    private DBUser sender;
    private DBUser receiver;
    private Transaction transaction;

    /**
     * Sets up.
     */
    @BeforeAll
    public static void setUp() {
        transactionMapper = Mappers.getMapper(TransactionMapper.class);
    }

    /**
     * Sets up per test.
     */
    @BeforeEach
    public void setUpPerTest() {
        sender = new DBUser();
        sender.setUserId(1);
        sender.setEmail("receiver@mail.fr");
        sender.setUserName("receiverUserName");
        sender.setSolde(1000.0);

        receiver = new DBUser();
        receiver.setUserId(2);
        receiver.setEmail("sender@mail.fr");
        receiver.setUserName("senderUserName");
        receiver.setSolde(2000.0);

        transaction = new Transaction();
        transaction.setTransactionId(1);
        transaction.setDescription("Description");
        transaction.setAmount(100.0);
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
    }

    /**
     * Testing method toTransactionWithDebitCredit
     * - Given Sender userId and a Transaction
     * - When toTransactionWithDebitCredit
     * - Then return a TransactionWithDebitCreditDto set as Debit
     */
    @Test
    public void givenSenderUserIdAndTransaction_whenToTransactionWithDebitCredit_thenReturnTransactionWithDebitCreditDtoAsDebit() {

        // Given

        // When
        TransactionWithDebitCreditDto dto = transactionMapper.toTransactionWithDebitCredit(sender.getUserId(), transaction);

        // Then
        assertEquals(transaction.getDescription(), dto.getDescription());
        assertEquals(transaction.getAmount(), dto.getAmount());
        assertEquals(receiver.getUserName(), dto.getRelationUserName());
        assertTrue(dto.isDebit());

    }

    /**
     * Testing method toTransactionWithDebitCredit
     * - Given Receiver userId and a Transaction
     * - When toTransactionWithDebitCredit
     * - Then return a TransactionWithDebitCreditDto set as Credit
     */
    @Test
    public void givenReceiverUserIdAndTransaction_whenToTransactionWithDebitCredit_thenReturnTransactionWithDebitCreditDtoAsCredit() {

        // Given

        // When
        TransactionWithDebitCreditDto dto = transactionMapper.toTransactionWithDebitCredit(receiver.getUserId(), transaction);

        // Then
        assertEquals(transaction.getDescription(), dto.getDescription());
        assertEquals(transaction.getAmount(), dto.getAmount());
        assertEquals(sender.getUserName(), dto.getRelationUserName());
        assertFalse(dto.isDebit());

    }
}
