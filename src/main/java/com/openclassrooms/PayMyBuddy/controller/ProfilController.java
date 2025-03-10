package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.exceptions.UserWithSameEmailExistsException;
import com.openclassrooms.PayMyBuddy.exceptions.UserWithSameUserNameExistsException;
import com.openclassrooms.PayMyBuddy.dto.UserDto;
import com.openclassrooms.PayMyBuddy.service.IDBUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
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

    /**
     * Constructor.
     *
     * @param userService the user service
     */
    public ProfilController(IDBUserService userService) {
        this.userService = userService;
    }

    /**
     * Display the profil page.
     *
     * @param model the model
     * @param user  the user
     * @return the profil page
     */
    @GetMapping
    public String profil(Model model, @AuthenticationPrincipal User user) {
        log.info("====> GET /profil  : page for user {} <====", user.getUsername());
        model.addAttribute("highlightProfil", true);

        UserDto userDto = userService.findByEmail(user.getUsername());
        model.addAttribute("userDto", userDto);

        return "profil";
    }

    /**
     * Update the user profil.
     *
     * @param userDto            the user to update
     * @param bindingResult      the binding result
     * @param model              the model
     * @param user               the user
     * @param httpServletRequest the http servlet request
     * @return the login page
     * @throws ServletException if an error occurs
     */
    @PostMapping
    public String profil(@Valid @ModelAttribute UserDto userDto,
                         BindingResult bindingResult,
                         Model model,
                         @AuthenticationPrincipal User user, HttpServletRequest httpServletRequest) throws ServletException {
        log.info("====> POST /profil : page for user {} <====", user.getUsername());
        model.addAttribute("highlightProfil", true);

        // Check form errors
        if (bindingResult.hasErrors()) {
            log.info("====> POST /profil : form contains error <====");
            return "profil";
        }

        // Save user in DB
        try {
            userService.updateUser(user.getUsername(), userDto);
        } catch (UserWithSameEmailExistsException | UserWithSameUserNameExistsException e) {
            log.debug("====> POST /profil : exception while updating profil {} <====", e.getMessage());

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

        log.info("====> POST /profil : profil updated and user logout <====");
        httpServletRequest.logout();
        return "redirect:/login";

    }


}
