package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.dto.RelationDto;
import com.openclassrooms.PayMyBuddy.exceptions.UserNotFoundException;
import com.openclassrooms.PayMyBuddy.exceptions.UserRelationException;
import com.openclassrooms.PayMyBuddy.repository.DBUserRepository;
import com.openclassrooms.PayMyBuddy.service.IDBUserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping(path = "/relation")
public class RelationController {

    private final IDBUserService userService;

    public RelationController(IDBUserService userService, DBUserRepository dbUserRepository) {
        this.userService = userService;
    }


    @GetMapping
    public String relations(Model model) {
        log.info("====> GET /relation page <====");

        model.addAttribute("highlightRelations", true);
        model.addAttribute("relationDto", new RelationDto());


        return "relation";
    }


    @PostMapping
    public String relations(@Valid @ModelAttribute RelationDto relationDto,
                            BindingResult bindingResult, Model model,
                            @AuthenticationPrincipal User user) {

        log.info("====> POST /relation page {} <====", relationDto.getEmail());
        model.addAttribute("highlightRelations", true);

        if (bindingResult.hasErrors()) {
            log.info("====> Email bad format <====");
            return "relation";
        }

        try {
            userService.addRelation(user.getUsername(), relationDto.getEmail());
        } catch (UserNotFoundException | UserRelationException e) {
            log.debug("====> Error creating relation : {} <====", e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "relation";
        }

        log.info("====> Relations added <====");
        return "redirect:/relation?success";
    }
}
