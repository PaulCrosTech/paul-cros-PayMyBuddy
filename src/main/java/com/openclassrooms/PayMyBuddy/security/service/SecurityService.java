package com.openclassrooms.PayMyBuddy.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


/**
 * Security Service
 */
@Slf4j
@Service
public class SecurityService {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityService(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Reauthenticate the user with the new data.
     *
     * @param email the email of the user
     */
    public void reauthenticateUser(String email) {
        log.info("====> Reauthenticate user with email {} <====", email);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    /**
     * Get the current authentication email.
     *
     * @return the email of the current authentication
     */
    public String getAuthenticationEmail() {
        log.info("====> Get the current authentication <====");
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
