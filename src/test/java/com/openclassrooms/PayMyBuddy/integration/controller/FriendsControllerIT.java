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
public class FriendsControllerIT extends AbstractContainerDB {


    /**
     * Test of friends page
     *
     * @throws Exception exception
     */
    @Test
    public void givenAuthenticatedUser_whenAccessToFriendsPage_thenReturnFriendsPage() throws Exception {

        mockMvc.perform(get("/friends"))
                .andExpect(status().isOk())
                .andExpect(view().name("friends"));
    }
    
}
