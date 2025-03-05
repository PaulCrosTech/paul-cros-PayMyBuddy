package com.openclassrooms.PayMyBuddy.integration.controller;

import com.openclassrooms.PayMyBuddy.integration.config.AbstractContainerDB;
import com.openclassrooms.PayMyBuddy.dto.UserDto;
import com.openclassrooms.PayMyBuddy.repository.DBUserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Integration tests for ProfilController
 */
@WithMockUser(username = "alice@mail.fr")
public class ProfilControllerIT extends AbstractContainerDB {


    @Autowired
    private DBUserRepository dbUserRepository;

    /**
     * Test of profil page
     * - Given : authenticated user
     * - When GET /profil
     * - Then return profil page
     *
     * @throws Exception exception
     */
    @Test
    public void givenAuthenticatedUser_whenAccessToProfilPage_thenReturnProfilPage() throws Exception {

        MvcResult result = mockMvc.perform(get("/profil"))
                .andExpect(status().isOk())
                .andExpect(view().name("profil"))
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        UserDto userDto = (UserDto) modelAndView.getModel().get("userDto");

        assertThat(userDto.getUserName()).isEqualTo("alice");
        assertThat(userDto.getEmail()).isEqualTo("alice@mail.fr");
    }


    /**
     * Test of update profil
     * - Given : auth user
     * - When POST /profil
     * - Then profil is updated
     *
     * @throws Exception exception
     */
    @Test
    @Rollback
    @Transactional
    public void givenValidInformation_whenUpdateProfil_thenProfilIsUpdated() throws Exception {
        mockMvc.perform(post("/profil")
                        .with(csrf().asHeader())
                        .param("userName", "validUserName")
                        .param("email", "validMail@mail.fr")
                        .param("password", "Password@1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"))
                .andReturn();

        dbUserRepository.findByEmail("validMail@mail.fr")
                .ifPresentOrElse(
                        user -> {
                            assertThat(user.getUserName()).isEqualTo("validUserName");
                        },
                        () -> {
                            throw new AssertionError("User not found");
                        });
    }

    /**
     * Test of update profil
     * - Given : auth user
     * - When POST /profil with existing username
     * - Then profil is not updated
     *
     * @throws Exception exception
     */
    @Test
    public void givenExistingUserName_whenUpdateProfil_thenProfilIsNotUpdated() throws Exception {
        mockMvc.perform(post("/profil")
                        .with(csrf().asHeader())
                        .param("userName", "bob")
                        .param("email", "alice@mail.fr")
                        .param("password", "Password@1"))
                .andExpect(status().isOk())
                .andExpect(view().name("profil"));

        dbUserRepository.findByEmail("alice@mail.fr")
                .ifPresentOrElse(
                        user -> {
                            assertThat(user.getUserName()).isEqualTo("alice");
                        },
                        () -> {
                            throw new AssertionError("User not found");
                        });
    }

    /**
     * Test of update profil
     * - Given : auth user
     * - When POST /profil with existing mail
     * - Then profil is not updated
     *
     * @throws Exception exception
     */
    @Test
    public void givenExistingMail_whenUpdateProfil_thenProfilIsNotUpdated() throws Exception {
        mockMvc.perform(post("/profil")
                        .with(csrf().asHeader())
                        .param("userName", "alice")
                        .param("email", "bob@mail.fr")
                        .param("password", "Password@1"))
                .andExpect(status().isOk())
                .andExpect(view().name("profil"));

        dbUserRepository.findByEmail("alice@mail.fr")
                .ifPresentOrElse(
                        user -> {
                            assertThat(user.getUserName()).isEqualTo("alice");
                        },
                        () -> {
                            throw new AssertionError("User not found");
                        });
    }

}
