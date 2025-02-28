package com.openclassrooms.PayMyBuddy.integration.security;

import com.openclassrooms.PayMyBuddy.integration.config.AbstractContainerDB;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UnauthorizedAccessIT extends AbstractContainerDB {


    /**
     * Test of unauthorized access to secured pages
     * - Given : unauthenticated user
     * - When GET "/page"
     * - Then redirected to login page
     *
     * @param securedPage the url of the page
     * @throws Exception exception
     */
    @ParameterizedTest
    @ValueSource(strings = {"/profil", "/transfer", "/relation"})
    public void givenUnauthenticatedUser_whenAccessToSecuredPage_thenRedirectToLoginPage(String securedPage) throws Exception {
        mockMvc.perform(get(securedPage))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }
}
