package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.dto.TransactionDto;
import com.openclassrooms.PayMyBuddy.dto.TransactionWithDebitCreditDto;
import com.openclassrooms.PayMyBuddy.entity.DBUser;
import com.openclassrooms.PayMyBuddy.exceptions.TransactionInsufficientBalanceException;
import com.openclassrooms.PayMyBuddy.service.IDBUserService;
import com.openclassrooms.PayMyBuddy.service.ITransactionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for the transaction page.
 */
@Slf4j
@Controller
@RequestMapping(path = "/transaction")
public class TransactionController {

    private final IDBUserService userService;
    private final ITransactionService transactionService;
    private static final List<Integer> TRANSACTIONS_PER_PAGE = List.of(5, 10, 15, 20);

    public TransactionController(IDBUserService userService,
                                 ITransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    /**
     * Display the transaction page.
     *
     * @return the transaction page.
     */
    @GetMapping
    public String transaction(
            Model model,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) Integer pageSize,
            @AuthenticationPrincipal User user) {

        log.info("====> GET /transaction page <====");
        setupModel(model, new TransactionDto(), user, page, pageSize);

        // TODO : revoir les DTO entre dto de formulaire et dto d'entity bref y a un truc qui va pas
        // TODO : revoir le nommage parfois je dis transaction parfois transaction

        // TODO : faire les tests unitaires de partout ... youpi y'en a bcp

        // TODO : modifier le script d'init de la base de données pour rajouter bcp de transactions (cf HS software apparement  y a une fonctionnalité)
        // TODO : l'entity faudra aussi la modifier pour qu'elle crée automatiquement la base de données !


        return "transaction";
    }

    /**
     * @param transactionDto the transaction form data.
     * @param bindingResult  the binding result.
     * @param model          the model.
     * @param user           the authenticated user.
     * @return the transaction page.
     */
    @PostMapping
    public String transaction(
            Model model,
            @Valid @ModelAttribute TransactionDto transactionDto,
            BindingResult bindingResult,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) Integer pageSize,
            @AuthenticationPrincipal User user) {

        log.info("====> POST /transaction page <====");
        setupModel(model, transactionDto, user, page, pageSize);

        if (bindingResult.hasErrors()) {
            log.debug("====> Error in transaction form <====");
            log.debug(bindingResult.getAllErrors().toString());
            return "transaction";
        }

        try {
            transactionService.save(user.getUsername(), transactionDto);
        } catch (TransactionInsufficientBalanceException e) {

            log.error("====> Error in transaction form : {} <====", e.getMessage());
            model.addAttribute("savingTransactionError", e.getMessage());
            setupModel(model, transactionDto, user, page, pageSize);
            return "transaction";
        }

        log.info("====> transaction done <====");
        return "redirect:/transaction?success";
    }

    /**
     * Set up the model with the transaction form data and the transactions.
     *
     * @param model    the model.
     * @param user     the authenticated user.
     * @param page     the parameter page number.
     * @param pageSize the parameter page size.
     */
    private void setupModel(Model model, TransactionDto transactionDto, User user, int page, Integer pageSize) {

        if (pageSize == null) {
            pageSize = TRANSACTIONS_PER_PAGE.getFirst();
        }

        model.addAttribute("transactionDto", transactionDto);

        List<DBUser> connections = userService.getConnections(user.getUsername());
        model.addAttribute("connections", connections);

        Pageable pageable = Pageable.ofSize(pageSize).withPage(page - 1);
        Page<TransactionWithDebitCreditDto> transactions = transactionService.findAsSendOrReceiverByEmail(user.getUsername(), pageable);

        model.addAttribute("transactions", transactions.getContent());
        model.addAttribute("currentPage", transactions.getNumber() + 1);
        model.addAttribute("totalItems", transactions.getTotalElements());
        model.addAttribute("totalPages", transactions.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("transactionPerPage", TRANSACTIONS_PER_PAGE);

        model.addAttribute("highlightTransaction", true);
    }
}
