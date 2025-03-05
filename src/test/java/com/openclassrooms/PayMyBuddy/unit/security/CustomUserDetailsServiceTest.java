package com.openclassrooms.PayMyBuddy.unit.security;

import com.openclassrooms.PayMyBuddy.entity.DBUser;
import com.openclassrooms.PayMyBuddy.repository.DBUserRepository;
import com.openclassrooms.PayMyBuddy.security.service.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private DBUserRepository dbUserRepository;

    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    public void setUpPerTest() {
        customUserDetailsService = new CustomUserDetailsService(dbUserRepository);
    }

    /**
     * Testing method loadUserByUsername
     * Given valid email
     * When loadUserByUsername
     * Then return user
     */
    @Test
    public void givenValidEmail_whenLoadUserByUsername_thenReturnUser() {
        // Given
        DBUser dbUser = new DBUser();
        dbUser.setEmail("mail@mail.fr");
        dbUser.setPassword("Password@1");

        UserDetails userDetailsExpected = new User(
                dbUser.getEmail(),
                dbUser.getPassword(),
                new ArrayList<>()
        );

        when(dbUserRepository.findByEmail(dbUser.getEmail())).thenReturn(Optional.of(dbUser));

        // When
        UserDetails actualUser = customUserDetailsService.loadUserByUsername(dbUser.getEmail());

        // Then
        assertEquals(userDetailsExpected, actualUser);
    }

    /**
     * Testing method loadUserByUsername
     * Given unknow email
     * When loadUserByUsername
     * Then throw UsernameNotFoundException
     */
    @Test
    public void givenUnknowEmail_whenLoadUserByEmail_thenThrowUsernameNotFoundException() {
        // Given
        String email = "unknow@mail.fr";

        // When && Then
        assertThrows(
                UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername(email)
        );


    }
}
