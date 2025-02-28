package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.dto.TransactionWithDebitCreditDto;
import com.openclassrooms.PayMyBuddy.dto.TransferDto;
import com.openclassrooms.PayMyBuddy.entity.DBUser;
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
 * Controller for the transfer page.
 */
@Slf4j
@Controller
@RequestMapping(path = "/transfer")
public class TransferController {

    private final IDBUserService userService;
    private final ITransactionService transactionService;
    private static final List<Integer> TRANSACTIONS_PER_PAGE = List.of(5, 10, 15, 20);

    public TransferController(IDBUserService userService,
                              ITransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    /**
     * Display the transfer page.
     *
     * @return the transfer page.
     */
    @GetMapping
    public String transfer(
            Model model,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) Integer pageSize,
            @AuthenticationPrincipal User user) {
        log.info("====> GET /transfer page <====");
        setupModel(model, user, page, pageSize);

        // TODO : prévoir le cas où il n y pas de transactions, \
        //  Gérer le formulaire Et très certainement revoir le DTO ca ne va pas je pense.

        // TODO : faire les tests unitaires de partout ... youpi y'en a bcp

        // TODO : modifier le script d'init de la base de données pour rajouter bcp de transactions (cf HS software apparement  y a une fonctionnalité)
        // TODO : l'entity faudra aussi la modifier pour qu'elle crée automatiquement la base de données !


        // TODO : faire de tout ce basard une méthode privée du controller ?


        return "transfer";
    }

    /**
     * @param transferDto   the transfer form data.
     * @param bindingResult the binding result.
     * @param model         the model.
     * @param user          the authenticated user.
     * @return the transfer page.
     */
    @PostMapping
    public String transfer(@Valid @ModelAttribute TransferDto transferDto,
                           @RequestParam(defaultValue = "1") int page,
                           @RequestParam(required = false) Integer pageSize,
                           BindingResult bindingResult, Model model,
                           @AuthenticationPrincipal User user) {
        log.info("====> POST /transfer page <====");
        setupModel(model, user, page, pageSize);


        log.info("====> Transfer done <====");
        return "redirect:/transfer?success";
    }

    /**
     * Set up the model with the transfer form data and the transactions.
     *
     * @param model    the model.
     * @param user     the authenticated user.
     * @param page     the parameter page number.
     * @param pageSize the parameter page size.
     */
    private void setupModel(Model model, User user, int page, Integer pageSize) {

        if (pageSize == null) {
            pageSize = TRANSACTIONS_PER_PAGE.getFirst();
        }

        TransferDto transferDto = new TransferDto();
        List<DBUser> connections = userService.getConnections(user.getUsername());
        transferDto.setConnections(connections);

        Pageable pageable = Pageable.ofSize(pageSize).withPage(page - 1);
        Page<TransactionWithDebitCreditDto> transactions = transactionService.findAsSendOrReceiverByEmail(user.getUsername(), pageable);
        transferDto.setTransactions(transactions.getContent());

        model.addAttribute("transferDto", transferDto);

        model.addAttribute("currentPage", transactions.getNumber() + 1);
        model.addAttribute("totalItems", transactions.getTotalElements());
        model.addAttribute("totalPages", transactions.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("transactionPerPage", TRANSACTIONS_PER_PAGE);

        model.addAttribute("highlightTransfer", true);
    }
}
