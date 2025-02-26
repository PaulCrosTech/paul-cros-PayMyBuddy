package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.exceptions.UserNotFoundException;
import com.openclassrooms.PayMyBuddy.exceptions.UserRelationException;
import com.openclassrooms.PayMyBuddy.model.forms.RelationsForm;
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
@RequestMapping(path = "/relations")
public class RelationsController {

    private final IDBUserService userService;

    public RelationsController(IDBUserService userService, DBUserRepository dbUserRepository) {
        this.userService = userService;
    }


    @GetMapping
    public String relations(Model model) {
        log.info("====> GET /relations page <====");

        model.addAttribute("highlightRelations", true);
        model.addAttribute("relationsForm", new RelationsForm());


        return "relations";
    }


    @PostMapping
    public String relations(@Valid @ModelAttribute RelationsForm relationsForm,
                            BindingResult bindingResult, Model model,
                            @AuthenticationPrincipal User user) {

        log.info("====> POST /relations page {} <====", relationsForm.getEmail());
        model.addAttribute("highlightRelations", true);

        if (bindingResult.hasErrors()) {
            log.info("====> Email bad format <====");
            return "relations";
        }

        try {
            userService.addRelation(user.getUsername(), relationsForm.getEmail());
        } catch (UserNotFoundException | UserRelationException e) {
            log.debug("====> Error creating relation : {} <====", e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "relations";
        }

        log.info("====> Relations added <====");
        return "redirect:/relations?success";
    }
}
