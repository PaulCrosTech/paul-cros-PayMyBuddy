package com.openclassrooms.PayMyBuddy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for the login page.
 */
@Slf4j
@Controller
public class LoginController {

    /**
     * Display the login page.
     *
     * @return the login page.
     */
    @GetMapping(path = "/login")
    public String login(Model model) {
        log.info("====> GET /login page <====");

        model.addAttribute("highlightLogin", true);

        return "login";
    }
}
