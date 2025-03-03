package com.openclassrooms.PayMyBuddy.integration.controller;

import com.openclassrooms.PayMyBuddy.integration.config.AbstractContainerDB;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


/**
 * Integration tests for ProfilController
 */
@WithMockUser(username = "alice@mail.fr")
public class RelationControllerIT extends AbstractContainerDB {


    /**
     * Test if the relations page is displayed when the user is authenticated
     *
     * @throws Exception if the test fails
     */
    @Test
    public void givenAuthenticatedUser_whenAccessToRelationsPage_thenReturnRelationsPage() throws Exception {

        mockMvc.perform(get("/relation"))
                .andExpect(status().isOk())
                .andExpect(view().name("relation"));
    }

}
