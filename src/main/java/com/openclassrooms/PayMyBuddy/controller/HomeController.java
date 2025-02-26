package com.openclassrooms.PayMyBuddy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for the home page.
 */
@Slf4j
@Controller
@RequestMapping(path = "/")
public class HomeController {

    /**
     * Display the home page.
     *
     * @return the home page.
     */
    @GetMapping
    public String index() {
        log.info("====> GET / page <====");
        return "index";
    }
}
