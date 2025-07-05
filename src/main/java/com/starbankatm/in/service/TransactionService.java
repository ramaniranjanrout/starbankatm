package com.starbankatm.in.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starbankatm.in.model.Transaction;
import com.starbankatm.in.model.User;
import com.starbankatm.in.repository.TransactionRepository;
import com.starbankatm.in.repository.UserRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private UserRepository userRepo;

    public boolean processWithdraw(User user, double amount) {
        if (user.getBalance() < amount) return false;
        user.setBalance(user.getBalance() - amount);
        userRepo.save(user);

        transactionRepo.save(new Transaction(null, "WITHDRAW", amount, LocalDateTime.now(), user));
        return true;
    }

    public void processDeposit(User user, double amount) {
        user.setBalance(user.getBalance() + amount);
        userRepo.save(user);

        transactionRepo.save(new Transaction(null, "DEPOSIT", amount, LocalDateTime.now(), user));
    }

    public List<Transaction> getRecentTransactions(User user) {
        return transactionRepo.findTop5ByUserOrderByTimestampDesc(user);
    }

    public boolean changePin(User user, String oldPin, String newPin) {
        if (!user.getPin().equals(oldPin)) return false;
        user.setPin(newPin);
        userRepo.save(user);
        return true;
    }
}
