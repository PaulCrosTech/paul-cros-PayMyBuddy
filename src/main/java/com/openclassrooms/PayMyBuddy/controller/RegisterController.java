package com.openclassrooms.PayMyBuddy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for the registration page.
 */
@Slf4j
@Controller
public class RegisterController {

    @GetMapping(path = "/register")
    public String register(Model model) {
        log.info("====> GET /register page <====");
        model.addAttribute("highlightRegister", true);
        return "register";
    }
}
