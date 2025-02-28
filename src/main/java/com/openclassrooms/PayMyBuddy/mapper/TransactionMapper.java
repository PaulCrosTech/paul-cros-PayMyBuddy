package com.openclassrooms.PayMyBuddy.mapper;

import com.openclassrooms.PayMyBuddy.dto.TransactionWithDebitCreditDto;
import com.openclassrooms.PayMyBuddy.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "relationUserName",
            expression = "java(transaction.getSender().getUserId() == userId ? transaction.getReceiver().getUserName() : transaction.getSender().getUserName())")
    @Mapping(
            target = "debit",
            expression = "java(transaction.getSender().getUserId() == userId)"
    )
    TransactionWithDebitCreditDto toTransactionWithDebitCredit(int userId, Transaction transaction);

}
