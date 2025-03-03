package com.openclassrooms.PayMyBuddy.mapper;

import com.openclassrooms.PayMyBuddy.dto.TransactionWithDebitCreditDto;
import com.openclassrooms.PayMyBuddy.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Transaction Mapper
 */
@Mapper(componentModel = "spring")
public interface TransactionMapper {


    /**
     * Transaction to TransactionWithDebitCreditDto
     *
     * @param userId      the user id
     * @param transaction the transaction
     * @return the transaction with debit credit dto
     */
    @Mapping(target = "relationUserName",
            expression = "java(transaction.getSender().getUserId() == userId ? transaction.getReceiver().getUserName() : transaction.getSender().getUserName())")
    @Mapping(
            target = "debit",
            expression = "java(transaction.getSender().getUserId() == userId)"
    )
    TransactionWithDebitCreditDto toTransactionWithDebitCredit(int userId, Transaction transaction);

}
