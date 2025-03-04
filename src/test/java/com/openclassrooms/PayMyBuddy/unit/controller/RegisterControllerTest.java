package com.openclassrooms.PayMyBuddy.unit.controller;


import com.openclassrooms.PayMyBuddy.controller.RegisterController;
import com.openclassrooms.PayMyBuddy.dto.UserDto;
import com.openclassrooms.PayMyBuddy.exceptions.UserWithSameEmailExistsException;
import com.openclassrooms.PayMyBuddy.service.IDBUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = RegisterController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IDBUserService userService;


    /**
     * Test of the register method.
     * Given: Valid informations
     * When: Post /register
     * Then: Register user and redirect to login page
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenValidInformations_whenRegister_thenRegisterUserAndRedirectLoginPage() throws Exception {
        // Given
        String username = "test";
        String password = "Password@1";
        String email = "test@test.com";


        // When && Then
        mockMvc.perform(post("/register")
                        .param("userName", username)
                        .param("password", password)
                        .param("email", email)
                        .with(csrf().asHeader()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?registered"));
    }

    /**
     * Test of the register method.
     * Given: Invalid informations
     * When: Post /register
     * Then: Return the register page with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenInvalidInformations_whenRegister_thenReturnRegisterPage() throws Exception {
        // Given
        String username = "test";
        String password = "badpassword";
        String email = "test@mail.fr";

        // When && Then
        mockMvc.perform(post("/register")
                        .param("userName", username)
                        .param("password", password)
                        .param("email", email)
                        .with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeHasFieldErrorCode("userDto", "password", "ValidPassword"));

    }

    /**
     * Test of the register method.
     * Given: User with an email already used
     * When: Post /register
     * Then: Return the register page with error
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void givenUserWithSameEmailExists_whenRegister_thenReturnRegisterPage() throws Exception {
        // Given
        UserDto userDto = new UserDto();
        userDto.setEmail("test@test.com");
        userDto.setUserName("test");
        userDto.setPassword("Password@1");

        doThrow(new UserWithSameEmailExistsException(userDto.getEmail())).when(userService).addUser(userDto);

        // When && Then
        mockMvc.perform(post("/register")
                        .with(csrf().asHeader())
                        .param("userName", userDto.getUserName())
                        .param("password", userDto.getPassword())
                        .param("email", userDto.getEmail()))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeHasFieldErrorCode("userDto", "email", "error.userDto"));
    }

}
