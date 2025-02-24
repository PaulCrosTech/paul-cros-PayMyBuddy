package com.openclassrooms.PayMyBuddy.integration.controller;

import com.openclassrooms.PayMyBuddy.integration.config.AbstractContainerDB;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


/**
 * Integration tests for TransferController
 */
@WithMockUser(username = "alice@mail.fr")
public class TransferControllerIT extends AbstractContainerDB {


    /**
     * Test of transfer page
     *
     * @throws Exception exception
     */
    @Test
    public void givenAuthenticatedUser_whenAccessToTransferPage_thenReturnTransferPage() throws Exception {

        mockMvc.perform(get("/transfer"))
                .andExpect(status().isOk())
                .andExpect(view().name("transfer"));
    }


}
