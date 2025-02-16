package com.openclassrooms.PayMyBuddy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for the home page.
 */
@Slf4j
@Controller
public class HomeController {

    /**
     * Display the home page.
     * @return the home page.
     */
    @GetMapping(path = "/")
    public String index() {
        log.info("====> GET / page <====");
        return "index";
    }
}
