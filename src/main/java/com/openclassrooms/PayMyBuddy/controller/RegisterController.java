package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.exceptions.UserWithSameEmailExistsException;
import com.openclassrooms.PayMyBuddy.exceptions.UserWithSameUserNameExistsException;
import com.openclassrooms.PayMyBuddy.model.dto.UserDto;
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
     * @return the registration page.
     */
    @GetMapping
    public String register(Model model) {
        log.info("====> GET /register page <====");
        model.addAttribute("highlightRegister", true);
        model.addAttribute("userDto", new UserDto());
        return "register";
    }

    /**
     * Register a new user.
     *
     * @param userDto       the user to register
     * @param bindingResult the binding result
     * @return the login page
     */
    @PostMapping
    public String register(@Valid @ModelAttribute UserDto userDto, BindingResult bindingResult, Model model) {
        log.info("====> POST /register for user {} <====", userDto.getUserName());
        model.addAttribute("highlightRegister", true);

        // Check form errors
        if (bindingResult.hasErrors()) {
            log.debug("====> Error in registering user {} <====", userDto.getUserName());
            return "register";
        }

        // Save user in DB
        try {
            userService.addUser(userDto);
        } catch (UserWithSameEmailExistsException | UserWithSameUserNameExistsException e) {
            log.debug("====> Error in /register form : {} <====", e.getMessage());

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

        log.info("====> User {} is registered <====", userDto.getUserName());

        return "redirect:/login?registered";
    }
}
