package com.openclassrooms.PayMyBuddy.integration.controller;

import com.openclassrooms.PayMyBuddy.integration.config.AbstractContainerDB;
import org.junit.jupiter.api.Test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration test class for the HomeController class.
 */
public class LoginControllerIT extends AbstractContainerDB {


    /**
     * Test access to login page
     * - Given
     * - When GET /login
     * - Then login page is returned
     *
     * @throws Exception exception
     */
    @Test
    public void given_whenAccessToLoginPage_thenReturnLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    /**
     * Test of login method
     * - Given Existing email and password
     * - When POST /login
     * - Then user is authenticated and redirected to private page
     *
     * @throws Exception exception
     */
    @Test
    public void givenExistingUser_whenLogin_thenAuthenticatedAndRedirectedToPrivatePage() throws Exception {

        mockMvc.perform(post("/login")
                        .param("email", "alice@mail.fr")
                        .param("password", "Password@1")
                        .with(csrf().asHeader()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/transaction"));
    }

    /**
     * Test of login method
     * - Given Non-existing email / password
     * - When POST /login
     * - Then user is not authenticated and redirected to login page with error
     *
     * @throws Exception exception
     */
    @Test
    public void givenNonExistingUser_whenLogin_thenUserIsNotAuthenticated() throws Exception {

        mockMvc.perform(post("/login")
                        .param("email", "bad@mail.fr")
                        .param("password", "badPassword")
                        .with(csrf().asHeader()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }

    /**
     * Test of login method
     * - Given Non-authenticated user
     * - When access to a private page
     * - Then redirected to login page
     *
     * @throws Exception exception
     */
    @Test
    public void givenNonAuthenticatedUser_wheAccessPrivatePage_thenRedirectedToLoginPage() throws Exception {
        mockMvc.perform(get("/transfer"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

}
