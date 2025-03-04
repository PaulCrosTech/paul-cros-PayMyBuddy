package com.openclassrooms.PayMyBuddy.integration.controller;

import com.openclassrooms.PayMyBuddy.integration.config.AbstractContainerDB;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Integration test class for the HomeController class.
 */
public class HomeControllerIT extends AbstractContainerDB {


    /**
     * Test of the index method.
     *
     * @throws Exception if an error occurs.
     */
    @Test
    public void given_whenAccessToHomePage_thenReturnHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

}
