package com.openclassrooms.PayMyBuddy.integration.controller;

import com.openclassrooms.PayMyBuddy.integration.config.AbstractContainerDB;
import com.openclassrooms.PayMyBuddy.entity.DBUser;
import com.openclassrooms.PayMyBuddy.repository.DBUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Integration test class for the RegisterController class.
 */
public class RegisterControllerIT extends AbstractContainerDB {


    @Autowired
    private DBUserRepository dbUserRepository;

    /**
     * Test access to register page
     * - Given
     * - When GET /register
     * - Then register page is returned
     *
     * @throws Exception exception
     */
    @Test
    public void given_whenAccessToRegisterPage_thenReturnRegisterPage() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }


    /**
     * Test of register method
     * - Given : valid information to register a new user
     * - When POST /register
     * - Then user is registered and redirected to login page
     *
     * @throws Exception exception
     */
    @Test
    public void givenValidInformation_whenRegister_thenUserIsRegisteredAndRedirectedToLogin() throws Exception {

        mockMvc.perform(post("/register")
                        .param("userName", "newUserName")
                        .param("email", "newEmail@mail.fr")
                        .param("password", "Password@1")
                        .with(csrf().asHeader()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?registered"));

        Optional<DBUser> user = dbUserRepository.findByEmail("newEmail@mail.fr");
        assertTrue(user.isPresent());
        assertEquals("newUserName", user.get().getUserName());
    }

    /**
     * Test of register method
     * - Given : existing username
     * - When POST /register
     * - Then user is not registered and redirected to register page
     *
     * @throws Exception exception
     */
    @Test
    public void givenExistingUserName_whenRegister_thenUserIsNotRegisteredAndRedirectedToRegister() throws Exception {

        mockMvc.perform(post("/register")
                        .param("userName", "alice")
                        .param("email", "newAlice@mail.fr")
                        .param("password", "Password@1")
                        .with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeHasFieldErrorCode("userDto", "userName", "error.userDto"));
    }

    /**
     * Test of register method
     * - Given : existing email
     * - When POST /register
     * - Then user is not registered and redirected to register page
     *
     * @throws Exception exception
     */
    @Test
    public void givenExistingEmail_whenRegister_thenUserIsNotRegisteredAndRedirectedToRegister() throws Exception {

        mockMvc.perform(post("/register")
                        .param("userName", "newAlice")
                        .param("email", "alice@mail.fr")
                        .param("password", "Password@1")
                        .with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeHasFieldErrorCode("userDto", "email", "error.userDto"));
    }

    /**
     * Test of register method
     * - Given : Invalid password format
     * - When POST /register
     * - Then user is not registered and redirected to register page
     *
     * @throws Exception exception
     */
    @Test
    public void givenInvalidPasswordFormat_whenRegister_thenUserIsNotRegisteredAndRedirectedToRegister() throws Exception {

        mockMvc.perform(post("/register")
                        .param("userName", "newUserName2")
                        .param("email", "newEmail2@mail.fr")
                        .param("password", "badPasswordFormat")
                        .with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeHasFieldErrorCode("userDto", "password", "ValidPassword"));
    }


}
