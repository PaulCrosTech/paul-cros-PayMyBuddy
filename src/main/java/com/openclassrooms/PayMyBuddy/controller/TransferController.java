package com.openclassrooms.PayMyBuddy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for the transfer page.
 */
@Slf4j
@Controller
public class TransferController {

    /**
     * Display the transfer page.
     *
     * @return the transfer page.
     */
    @GetMapping(path = "/transfer")
    public String transfer(Model model) {
        log.info("====> GET /transfer page <====");

        model.addAttribute("highlightTransfer", true);

        return "transfer";
    }
}
