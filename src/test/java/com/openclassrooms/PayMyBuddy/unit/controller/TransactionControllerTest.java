package com.openclassrooms.PayMyBuddy.unit.controller;


import com.openclassrooms.PayMyBuddy.controller.TransactionController;
import com.openclassrooms.PayMyBuddy.dto.TransactionDto;
import com.openclassrooms.PayMyBuddy.exceptions.TransactionInsufficientBalanceException;
import com.openclassrooms.PayMyBuddy.service.IDBUserService;
import com.openclassrooms.PayMyBuddy.service.ITransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = TransactionController.class)
@WithMockUser
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IDBUserService userService;

    @MockitoBean
    private ITransactionService transactionService;


    @BeforeEach
    public void setUp() {
        when(userService.getConnectionsUserName(anyString())).thenReturn(Collections.emptyList());
        when(transactionService.findAsSendOrReceiverByEmail(anyString(), any(Pageable.class))).thenReturn(Page.empty());
    }


    /**
     * Test access to page Transaction with authenticated user
     * Given: Authenticated user
     * When: Get /transaction
     * Then: Return Transaction page
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenAuthenticatedUser_whenGetTransaction_thenReturnTransaction() throws Exception {
        // When && Then
        mockMvc.perform(get("/transaction"))
                .andExpect(status().isOk())
                .andExpect(view().name("transaction"));
    }


    /**
     * Test adding a transaction.
     * Given: Valid informations
     * When: Post /transaction
     * Then: Add transaction and redirect to transaction
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidInformations_whenAddTransaction_thenAddTransactionAndRedirectToTransaction() throws Exception {

        // Given
        doNothing().when(transactionService).save(anyString(), any(TransactionDto.class));

        // When && Then
        mockMvc.perform(post("/transaction")
                        .param("userName", "userName")
                        .param("description", "description test")
                        .param("amount", "100")
                        .with(csrf().asHeader()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/transaction?success"));
    }


    /**
     * Test adding a transaction.
     * Given: Invalid informations
     * When: Post /transaction
     * Then: Return transaction page with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenInvalidInformations_whenAddTransaction_thenReturnTransactionPageWithError() throws Exception {

        // When && Then
        mockMvc.perform(post("/transaction")
                        .param("userName", "")
                        .param("description", "")
                        .param("amount", "100")
                        .with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(view().name("transaction"))
                .andExpect(model().attributeHasFieldErrorCode("transactionDto", "description", "ValidDescription"))
                .andExpect(model().attributeHasFieldErrorCode("transactionDto", "userName", "ValidUsername"));

    }


    /**
     * Test adding a transaction.
     * Given: User with insufficient balance
     * When: Post /transaction
     * Then: Return transaction page with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenUserWithInsufficientBalance_whenAddTransaction_thenReturnTransactionPageWithError() throws Exception {
        // Given
        double balance = 0.00;
        double amount = 100.00;
        String userName = "userName";
        doThrow(new TransactionInsufficientBalanceException(userName, balance, amount))
                .when(transactionService).save(anyString(), any(TransactionDto.class));

        // When && Then
        mockMvc.perform(post("/transaction")
                        .param("userName", userName)
                        .param("description", "Valid description")
                        .param("amount", String.valueOf(amount))
                        .with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(view().name("transaction"))
                .andExpect(model().attributeExists("savingTransactionError"));
    }

}
