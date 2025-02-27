package com.openclassrooms.PayMyBuddy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * User class for the database.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class DBUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "username")
    private String userName;

    private String email;

    private String password;

    private double solde;

    @ManyToMany(
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "user_relations",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<DBUser> connections = new ArrayList<>();

}
