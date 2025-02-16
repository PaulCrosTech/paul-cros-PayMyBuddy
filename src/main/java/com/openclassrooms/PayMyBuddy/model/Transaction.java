package com.openclassrooms.PayMyBuddy.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Transaction class
 */
@Entity
@Data
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int transactionId;

    private String description;
    private double amount;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "user_id_sender")
    private DBUser sender;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "user_id_receiver")
    private DBUser receiver;
}
