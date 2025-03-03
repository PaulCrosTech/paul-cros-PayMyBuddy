package com.openclassrooms.PayMyBuddy.integration.controller;

import com.openclassrooms.PayMyBuddy.integration.config.AbstractContainerDB;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


/**
 * Integration tests for TransactionController
 */
@WithMockUser(username = "alice@mail.fr")
public class TransactionControllerIT extends AbstractContainerDB {


    /**
     * Test of transaction page
     *
     * @throws Exception exception
     */
    @Test
    public void givenAuthenticatedUser_whenAccessToTransactionPage_thenReturnTransactionPage() throws Exception {

        mockMvc.perform(get("/transaction"))
                .andExpect(status().isOk())
                .andExpect(view().name("transaction"));
    }


}
