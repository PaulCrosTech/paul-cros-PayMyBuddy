package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.exceptions.UserWithSameEmailExistsException;
import com.openclassrooms.PayMyBuddy.exceptions.UserWithSameUserNameExistsException;
import com.openclassrooms.PayMyBuddy.dto.UserDto;
import com.openclassrooms.PayMyBuddy.service.IDBUserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for the registration page.
 */
@Slf4j
@Controller
@RequestMapping(path = "/register")
public class RegisterController {


    private final IDBUserService userService;

    /**
     * Constructor.
     *
     * @param userService the user service
     */
    public RegisterController(IDBUserService userService) {
        this.userService = userService;
    }


    /**
     * Display the registration page.
     *
     * @param model the model
     * @return the registration page
     */
    @GetMapping
    public String register(Model model) {
        log.info("====> GET /register : page <====");
        model.addAttribute("highlightRegister", true);
        model.addAttribute("userDto", new UserDto());
        return "register";
    }


    /**
     * Register a new user.
     *
     * @param userDto       the user to register
     * @param bindingResult the binding result
     * @param model         the model
     * @return the registration page
     */
    @PostMapping
    public String register(@Valid @ModelAttribute UserDto userDto, BindingResult bindingResult, Model model) {
        log.info("====> POST /register : page <====");
        model.addAttribute("highlightRegister", true);

        // Check form errors
        if (bindingResult.hasErrors()) {
            log.info("====> POST /register : form contains error <====");
            return "register";
        }

        // Save user in DB
        try {
            userService.addUser(userDto);
        } catch (UserWithSameEmailExistsException | UserWithSameUserNameExistsException e) {
            log.debug("====> POST /register : exception while creating user  {} <====", e.getMessage());

            String field = e instanceof UserWithSameEmailExistsException ? "email" : "userName";
            String fieldValue = e instanceof UserWithSameEmailExistsException ? userDto.getEmail() : userDto.getUserName();

            bindingResult.addError(new FieldError(
                    "userDto",
                    field,
                    fieldValue,
                    false,
                    new String[]{"error.userDto"},
                    null,
                    e.getMessage()
            ));
            return "register";
        }

        log.info("====> POST /register : user is created <====");
        return "redirect:/login?registered";
    }
}
