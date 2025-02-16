package com.openclassrooms.PayMyBuddy.repository;

import com.openclassrooms.PayMyBuddy.model.DBUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface DBUser repository.
 */
@Repository
public interface DBUserRepository extends JpaRepository<DBUser, Integer> {

    /**
     * Find User by email
     *
     * @param email the email
     * @return the user
     */
    DBUser findByEmail(String email);


    /**
     * Find User by username
     *
     * @param userName the username
     * @return the user
     */
    DBUser findByUserName(String userName);
}
