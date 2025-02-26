package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.exceptions.UserWithSameEmailExistsException;
import com.openclassrooms.PayMyBuddy.exceptions.UserWithSameUserNameExistsException;
import com.openclassrooms.PayMyBuddy.model.dto.UserDto;
import com.openclassrooms.PayMyBuddy.service.IDBUserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for the profil page.
 */
@Slf4j
@Controller
@RequestMapping(path = "/profil")
public class ProfilController {

    private final IDBUserService userService;

    public ProfilController(IDBUserService userService) {
        this.userService = userService;
    }

    /**
     * Display the transfer page.
     *
     * @return the transfer page.
     */
    @GetMapping
    public String profil(Model model, @AuthenticationPrincipal User user) {
        log.info("====> GET /profil page for user {} <====", user);
        model.addAttribute("highlightProfil", true);

        UserDto userDto = userService.findByEmail(user.getUsername());
        model.addAttribute("userDto", userDto);

        return "profil";
    }

    /**
     * Update the user profil.
     *
     * @param userDto       the user to update
     * @param bindingResult the binding result
     * @param model         the model
     * @return the profil page
     */
    @PostMapping
    public String profil(@Valid @ModelAttribute UserDto userDto,
                         BindingResult bindingResult,
                         Model model,
                         @AuthenticationPrincipal User user) {
        log.info("====> POST /profil page <====");
        model.addAttribute("highlightProfil", true);

        // Check form errors
        if (bindingResult.hasErrors()) {
            log.debug("====> Error on updating profil <====");
            return "profil";
        }

        // Save user in DB
        try {
            userService.updateUser(user.getUsername(), userDto);
        } catch (UserWithSameEmailExistsException | UserWithSameUserNameExistsException e) {
            log.debug("====> Error in /profil form : {} <====", e.getMessage());

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
            return "profil";
        }

        log.info("====> Profil is updated <====");

        return "redirect:/profil?success";
    }


}
