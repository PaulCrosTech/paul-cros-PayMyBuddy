package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.exceptions.UserWithSameEmailExistsException;
import com.openclassrooms.PayMyBuddy.exceptions.UserWithSameUserNameExistsException;
import com.openclassrooms.PayMyBuddy.model.DBUser;
import com.openclassrooms.PayMyBuddy.model.dto.DBUserRegisterDto;
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

import java.util.Objects;

/**
 * Controller for the registration page.
 */
@Slf4j
@Controller
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
    @GetMapping(path = "/register")
    public String register(Model model) {
        log.info("====> GET /register page <====");
        model.addAttribute("highlightRegister", true);
        model.addAttribute("DBUserRegisterDto", new DBUserRegisterDto());
        return "register";
    }

    /**
     * Register a new user.
     *
     * @param userRegisterDto the user to register
     * @param bindingResult   the binding result
     * @return the login page
     */
    @PostMapping(path = "/register")
    public String register(@Valid @ModelAttribute DBUserRegisterDto userRegisterDto, BindingResult bindingResult, Model model) {
        log.info("====> POST /register for user {} <====", userRegisterDto.getUserName());
        model.addAttribute("highlightRegister", true);

        // Check form errors
        if (bindingResult.hasErrors()) {
            log.debug("====> Error in registering user {} <====", userRegisterDto.getUserName());

            bindingResult.getAllErrors().stream()
                    .filter(error -> Objects.equals(error.getCode(), "ValidPasswordMatches"))
                    .findFirst()
                    .ifPresent(error -> bindingResult.addError(new FieldError(
                            "userRegisterDto",
                            "confirmPassword",
                            userRegisterDto.getConfirmPassword(),
                            false,
                            new String[]{"error.userRegisterDto"},
                            null,
                            error.getDefaultMessage()
                    )));

            return "register";
        }

        // Save user in DB
        try {
            DBUser userCreated = userService.addUser(userRegisterDto);
        } catch (UserWithSameEmailExistsException | UserWithSameUserNameExistsException e) {
            log.debug("====> Error {} <====", e.getMessage());

            String field = e instanceof UserWithSameEmailExistsException ? "email" : "userName";
            String fieldValue = e instanceof UserWithSameEmailExistsException ? userRegisterDto.getEmail() : userRegisterDto.getUserName();

            bindingResult.addError(new FieldError(
                    "userRegisterDto",
                    field,
                    fieldValue,
                    false,
                    new String[]{"error.userRegisterDto"},
                    null,
                    e.getMessage()
            ));
            return "register";
        }

        log.info("====> User {} is registered <====", userRegisterDto.getUserName());

        return "redirect:/login?registered";
    }
}
