package com.openclassrooms.PayMyBuddy.security.service;

import com.openclassrooms.PayMyBuddy.model.DBUser;
import com.openclassrooms.PayMyBuddy.repository.DBUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Custom user details service
 */
@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final DBUserRepository dbUserRepository;


    /**
     * Constructor
     *
     * @param dbUserRepository dbUserRepository
     */
    public CustomUserDetailsService(DBUserRepository dbUserRepository) {
        this.dbUserRepository = dbUserRepository;
    }

    /**
     * Load user by email from database
     *
     * @param email username
     * @return UserDetails
     * @throws UsernameNotFoundException UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("====> Authenticate user with email {} <====", email);
        DBUser dbUser = dbUserRepository.findByEmail(email);
        if (dbUser == null) {
            log.debug("====> User with mail {} is not found <====", email);
            throw new UsernameNotFoundException("User with mail " + email + " is not found");
        }
        return new User(dbUser.getEmail(),
                dbUser.getPassword(),
                new ArrayList<>()
        );
    }

}
