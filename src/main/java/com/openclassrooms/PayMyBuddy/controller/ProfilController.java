package com.openclassrooms.PayMyBuddy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for the profil page.
 */
@Slf4j
@Controller
public class ProfilController {

    /**
     * Display the transfer page.
     *
     * @return the transfer page.
     */
    @GetMapping(path = "/profil")
    public String transfer(Model model) {
        log.info("====> GET /profil page <====");

        model.addAttribute("highlightProfil", true);

        return "profil";
    }
}
