package com.openclassrooms.PayMyBuddy.repository;

import com.openclassrooms.PayMyBuddy.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * The interface Transaction repository.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Page<Transaction> findBySender_UserIdOrReceiver_UserId(int senderUserId, int receiverUserId, Pageable pageable);
}
