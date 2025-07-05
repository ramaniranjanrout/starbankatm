package com.starbankatm.in.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.starbankatm.in.model.Transaction;
import com.starbankatm.in.model.User;
import com.starbankatm.in.service.TransactionService;

import jakarta.servlet.http.HttpSession;

@Controller
public class TransactionController {

    @Autowired
    private TransactionService txService;

    private User getLoggedUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    @GetMapping("/transaction")
    public String showTransaction(HttpSession session, Model model) {
        User user = getLoggedUser(session);
        if (user == null) return "redirect:/signin";

        model.addAttribute("username", user.getUsername());
        return "transaction";
    }

    @GetMapping("/withdraw")
    public String withdrawForm(HttpSession session, Model model) {
        User user = getLoggedUser(session);
        if (user == null) return "redirect:/signin";

        model.addAttribute("username", user.getUsername());
        return "withdraw";
    }

    @PostMapping("/processWithdraw")
    public String processWithdraw(@RequestParam double amount, HttpSession session) {
        User user = getLoggedUser(session);
        if (user == null) return "redirect:/signin";
        boolean success = txService.processWithdraw(user, amount);
        return success ? "success" : "error";
    }

    @GetMapping("/deposit")
    public String depositForm(HttpSession session, Model model) {
        User user = getLoggedUser(session);
        if (user == null) return "redirect:/signin";

        model.addAttribute("username", user.getUsername());
        return "deposit";
    }

    @PostMapping("/processDeposit")
    public String processDeposit(@RequestParam double amount, HttpSession session) {
        User user = getLoggedUser(session);
        if (user == null) return "redirect:/signin";

        txService.processDeposit(user, amount);
        return "success";
    }


    @GetMapping("/balance")
    public String balance(Model model, HttpSession session) {
        User user = getLoggedUser(session);
        if (user == null) return "redirect:/signin";

        model.addAttribute("balance", user.getBalance());
        model.addAttribute("username", user.getUsername());
        return "balance";
    }

    @GetMapping("/ministatement")
    public String statement(Model model, HttpSession session) {
        User user = getLoggedUser(session);
        if (user == null) return "redirect:/signin";

        List<Transaction> list = txService.getRecentTransactions(user);
        model.addAttribute("transactions", list);
        model.addAttribute("username", user.getUsername());
        return "ministatement";
    }

    @GetMapping("/changepin")
    public String changePinForm(HttpSession session, Model model) {
        User user = getLoggedUser(session);
        if (user == null) return "redirect:/signin";

        model.addAttribute("username", user.getUsername());
        return "changepin";
    }

    @PostMapping("/processChangePin")
    public String changePin(@RequestParam String oldPin, @RequestParam String newPin, HttpSession session) {
        User user = getLoggedUser(session);
        if (user == null) return "redirect:/signin";

        boolean changed = txService.changePin(user, oldPin, newPin);
        return changed ? "success" : "error";
    }

    @GetMapping("/exit")
    public String exit(HttpSession session) {
        session.invalidate();
        return "index";
    }
}
