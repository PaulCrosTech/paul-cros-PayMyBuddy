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

    /**
     * Find all transactions by sender user id or receiver user id order by transaction id desc
     *
     * @param senderUserId   the sender user id
     * @param receiverUserId the receiver user id
     * @param pageable       the pageable
     * @return the page list of transactions
     */
    Page<Transaction> findBySender_UserIdOrReceiver_UserId_OrderByTransactionIdDesc(int senderUserId, int receiverUserId, Pageable pageable);
}
