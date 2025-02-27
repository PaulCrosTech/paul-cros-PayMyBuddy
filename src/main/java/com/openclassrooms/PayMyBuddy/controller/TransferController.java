package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.entity.DBUser;
import com.openclassrooms.PayMyBuddy.dto.TransferFormDto;
import com.openclassrooms.PayMyBuddy.service.IDBUserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Controller for the transfer page.
 */
@Slf4j
@Controller
@RequestMapping(path = "/transfer")
public class TransferController {

    private final IDBUserService userService;

    public TransferController(IDBUserService userService) {
        this.userService = userService;
    }

    /**
     * Display the transfer page.
     *
     * @return the transfer page.
     */
    @GetMapping
    public String transfer(Model model, @AuthenticationPrincipal User user) {
        log.info("====> GET /transfer page <====");
        model.addAttribute("highlightTransfer", true);

        TransferFormDto transferFormDto = new TransferFormDto();
        List<DBUser> connections = userService.getConnections(user.getUsername());
        transferFormDto.setConnections(connections);

        model.addAttribute("transferFormDto", transferFormDto);
        return "transfer";
    }

    @PostMapping
    public String transfer(@Valid @ModelAttribute TransferFormDto transferFormDto,
                           BindingResult bindingResult, Model model,
                           @AuthenticationPrincipal User user) {
        log.info("====> POST /transfer page <====");
        model.addAttribute("highlightTransfer", true);

        log.info("====> Transfer done <====");
        return "redirect:/transfer?success";
    }
}
