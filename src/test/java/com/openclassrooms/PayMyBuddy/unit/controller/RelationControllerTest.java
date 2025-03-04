package com.openclassrooms.PayMyBuddy.unit.controller;


import com.openclassrooms.PayMyBuddy.controller.RelationController;
import com.openclassrooms.PayMyBuddy.exceptions.UserRelationException;
import com.openclassrooms.PayMyBuddy.service.IDBUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = RelationController.class)
@WithMockUser
public class RelationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IDBUserService userService;


    /**
     * Test access to page Relation with authenticated user
     * Given: Authenticated user
     * When: Get /relation
     * Then: Return Relation page
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenAuthenticatedUser_whenGetRelation_thenReturnRelation() throws Exception {
        // When && Then
        mockMvc.perform(get("/relation"))
                .andExpect(status().isOk())
                .andExpect(view().name("relation"));
    }


    /**
     * Test add relation with valid informations
     * Given: Valid informations
     * When: Post /relation
     * Then: Add relation and redirect to relation
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidInformations_whenAddRelation_thenAddRelationAndRedirectToRelation() throws Exception {

        // Given
        doNothing().when(userService).addRelation(anyString(), anyString());

        // When && Then
        mockMvc.perform(post("/relation")
                        .param("email", "test@mail.fr")
                        .with(csrf().asHeader()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/relation?success"));
    }


    /**
     * Test add relation with invalid informations
     * Given: Invalid informations
     * When: Post /relation
     * Then: Return relation page with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenInvalidInformations_whenAddRelation_thenReturnRelationPageWithError() throws Exception {

        // When && Then
        mockMvc.perform(post("/relation")
                        .param("email", "badEmail")
                        .with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(view().name("relation"))
                .andExpect(model().attributeHasFieldErrorCode("relationDto", "email", "Email"));

    }


    /**
     * Test add relation with user with same email
     * Given: User with same email exists
     * When: Post /relation
     * Then: Return relation page with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenUserWithSameEmailExists_whenAddRelation_thenReturnProfilPageWithError() throws Exception {
        // Given
        doThrow(new UserRelationException("")).when(userService).addRelation(anyString(), anyString());

        // When && Then
        mockMvc.perform(post("/relation")
                        .with(csrf().asHeader())
                        .param("email", "mail@mail.fr"))
                .andExpect(status().isOk())
                .andExpect(view().name("relation"))
                .andExpect(model().attributeExists("error"));
    }

}
