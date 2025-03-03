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
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"username"}, name = "username_UNIQUE"),
                @UniqueConstraint(columnNames = {"email"}, name = "email_UNIQUE")
        }
)
public class DBUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "username", unique = true, nullable = false, length = 45)
    private String userName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, columnDefinition = "CHAR(60)")
    private String password;

    @Column(nullable = false)
    private double solde;

    @ManyToMany(
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "user_relations",
            joinColumns = @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_id")),
            inverseJoinColumns = @JoinColumn(name = "friend_id", foreignKey = @ForeignKey(name = "fk_friend_id")),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "friend_id"}, name = "user_friend_UNIQUE")
    )
    private List<DBUser> connections = new ArrayList<>();

}
