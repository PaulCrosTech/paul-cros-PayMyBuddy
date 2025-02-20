package com.openclassrooms.PayMyBuddy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for the friends page.
 */
@Slf4j
@Controller
public class FriendsController {

    /**
     * Display the transfer page.
     *
     * @return the transfer page.
     */
    @GetMapping(path = "/friends")
    public String transfer(Model model) {
        log.info("====> GET /friends page <====");

        model.addAttribute("highlightFriends", true);

        return "friends";
    }
}
