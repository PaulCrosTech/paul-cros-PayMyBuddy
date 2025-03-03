package com.openclassrooms.PayMyBuddy.entity;

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

    @Column(nullable = false, length = 45)
    private String description;

    @Column(nullable = false)
    private double amount;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "user_id_sender",
            foreignKey = @ForeignKey(name = "fk_user_id_sender")
    )
    private DBUser sender;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "user_id_receiver",
            foreignKey = @ForeignKey(name = "fk_user_id_receiver")
    )
    private DBUser receiver;

}
