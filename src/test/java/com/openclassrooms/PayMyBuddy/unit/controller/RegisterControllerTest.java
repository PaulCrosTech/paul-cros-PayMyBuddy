package com.openclassrooms.PayMyBuddy.unit.controller;


import com.openclassrooms.PayMyBuddy.controller.RegisterController;
import com.openclassrooms.PayMyBuddy.service.IDBUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers = RegisterController.class)
public class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IDBUserService userService;


}
