package com.openclassrooms.PayMyBuddy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for the login page.
 */
@Slf4j
@Controller
@RequestMapping(path = "/login")
public class LoginController {

    /**
     * Display the login page.
     *
     * @return the login page.
     */
    @GetMapping
    public String login(Model model) {
        log.info("====> GET /login page <====");

        model.addAttribute("highlightLogin", true);

        return "login";
    }

}
