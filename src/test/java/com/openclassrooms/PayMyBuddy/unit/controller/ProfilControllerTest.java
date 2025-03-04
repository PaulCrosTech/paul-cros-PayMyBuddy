package com.openclassrooms.PayMyBuddy.unit.controller;


import com.openclassrooms.PayMyBuddy.controller.ProfilController;
import com.openclassrooms.PayMyBuddy.dto.UserDto;
import com.openclassrooms.PayMyBuddy.exceptions.UserWithSameEmailExistsException;
import com.openclassrooms.PayMyBuddy.service.IDBUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = ProfilController.class)
@WithMockUser
public class ProfilControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IDBUserService userService;


    /**
     * Test access to page Profil with authenticated user
     * Given: Authenticated user
     * When: Get /profil
     * Then: Return Profil page
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenAuthenticatedUser_whenGetProfil_thenReturnProfil() throws Exception {

        // Given
        UserDto userDto = new UserDto();
        userDto.setEmail("test@mail.fr");
        userDto.setUserName("test");
        userDto.setPassword("Password@1");
        when(userService.findByEmail(anyString())).thenReturn(userDto);

        // When && Then
        mockMvc.perform(get("/profil"))
                .andExpect(status().isOk())
                .andExpect(view().name("profil"));
    }


    /**
     * Test updating Profil method.
     * Given: Valid informations
     * When: Post /profil
     * Then: Update Profil and logout
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidInformations_whenUpdateProfil_thenUpdateProfilAndLogout() throws Exception {

        // Given
        doNothing().when(userService).updateUser(anyString(), any(UserDto.class));

        // When && Then
        mockMvc.perform(post("/profil")
                        .param("userName", "test")
                        .param("email", "test@mail.fr")
                        .param("password", "Password@1")
                        .with(csrf().asHeader()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }


    /**
     * Test updating Profil method.
     * Given: Invalid informations
     * When: Post /profil
     * Then: Return Profil page with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenInvalidInformations_whenUpdateProfil_thenReturnProfilPageWithError() throws Exception {
        // Given
        String username = "test";
        String password = "badpassword";
        String email = "test@mail.fr";

        // When && Then
        mockMvc.perform(post("/profil")
                        .param("userName", username)
                        .param("password", password)
                        .param("email", email)
                        .with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(view().name("profil"))
                .andExpect(model().attributeHasFieldErrorCode("userDto", "password", "ValidPassword"));

    }


    /**
     * Test updating Profil method.
     * Given: User with an email already used
     * When: Post /profil
     * Then: Return Profil page with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenUserWithSameEmailExists_whenUpdateProfil_thenReturnProfilPageWithError() throws Exception {
        // Given
        UserDto userDto = new UserDto();
        userDto.setEmail("test@test.com");
        userDto.setUserName("test");
        userDto.setPassword("Password@1");

        doThrow(new UserWithSameEmailExistsException(userDto.getEmail())).when(userService).updateUser(anyString(), any(UserDto.class));

        // When && Then
        mockMvc.perform(post("/profil")
                        .with(csrf().asHeader())
                        .param("userName", userDto.getUserName())
                        .param("password", userDto.getPassword())
                        .param("email", userDto.getEmail()))
                .andExpect(status().isOk())
                .andExpect(view().name("profil"))
                .andExpect(model().attributeHasFieldErrorCode("userDto", "email", "error.userDto"));
    }

}
