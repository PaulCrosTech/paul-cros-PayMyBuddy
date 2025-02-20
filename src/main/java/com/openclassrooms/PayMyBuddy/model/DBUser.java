package com.openclassrooms.PayMyBuddy.model;

import com.openclassrooms.PayMyBuddy.validators.annotations.ValidPassword;
import com.openclassrooms.PayMyBuddy.validators.annotations.ValidPasswordMatches;
import com.openclassrooms.PayMyBuddy.validators.annotations.ValidUsername;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * User class
 */
@Entity
@Data
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
