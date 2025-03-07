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

    /**
     * Constructor.
     *
     * @param userService        the user service
     * @param transactionService the transaction service
     */
    public TransactionController(IDBUserService userService,
                                 ITransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    /**
     * Display the transaction page.
     *
     * @param model    the model
     * @param page     the page number
     * @param pageSize the page size
     * @param user     the authenticated user
     * @return the transaction page
     */
    @GetMapping
    public String transaction(
            Model model,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) Integer pageSize,
            @AuthenticationPrincipal User user) {

        log.info("====> GET /transaction  : page for user {} <====", user.getUsername());
        setupModel(model, new TransactionDto(), user, page, pageSize);

        return "transaction";
    }


    /**
     * Create a transaction.
     *
     * @param model          the model
     * @param transactionDto the transaction to create
     * @param bindingResult  the binding result
     * @param page           the page number
     * @param pageSize       the page size
     * @param user           the authenticated user
     * @return the transaction page
     */
    @PostMapping
    public String transaction(
            Model model,
            @Valid @ModelAttribute TransactionDto transactionDto,
            BindingResult bindingResult,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) Integer pageSize,
            @AuthenticationPrincipal User user) {

        log.info("====> POST /transaction : page for user {} <====", user.getUsername());
        setupModel(model, transactionDto, user, page, pageSize);

        if (bindingResult.hasErrors()) {
            log.info("====> POST /transaction : form contains error <====");
            return "transaction";
        }

        try {
            transactionService.save(user.getUsername(), transactionDto);
        } catch (TransactionInsufficientBalanceException e) {

            log.debug("====> POST /transaction : exception while creating transaction {} <====", e.getMessage());

            model.addAttribute("savingTransactionError", e.getMessage());
            setupModel(model, transactionDto, user, page, pageSize);

            return "transaction";
        }

        log.info("====> POST /transaction : transaction created <====");
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

        // TODO : récupérer uniquement le username
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
