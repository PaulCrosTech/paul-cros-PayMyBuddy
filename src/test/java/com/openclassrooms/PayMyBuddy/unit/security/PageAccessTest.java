package com.openclassrooms.PayMyBuddy.unit.security;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit test class for the UnauthorizedAccess class.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PageAccessTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test of the access to secured pages.
     * - Given : unauthenticated user
     * - When : GET "/profil", "/transaction" or "/relation"
     * - Then : redirected to login page
     *
     * @param securedPage the secured page
     * @throws Exception Exception
     */
    @ParameterizedTest
    @ValueSource(strings = {"/profil", "/transaction", "/relation"})
    public void givenUnauthenticatedUser_whenAccessToSecuredPage_thenRedirectToLoginPage(String securedPage) throws Exception {
        mockMvc.perform(get(securedPage))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    /**
     * Test of the access to non-secured pages.
     * - Given : unauthenticated user
     * - When : GET "/", "/register"
     * - Then : return the page
     *
     * @param securedPage the secured page
     * @param viewName    the view name
     * @throws Exception Exception
     */
    @ParameterizedTest
    @CsvSource({
            "'/', 'index'",
            "'/register', 'register'",
            "'/login', 'login'"
    })
    public void givenUnauthenticatedUser_whenAccessNonSecuredPage_thenReturnPage(String securedPage, String viewName) throws Exception {
        mockMvc.perform(get(securedPage))
                .andExpect(status().isOk())
                .andExpect(view().name(viewName));
    }

}
