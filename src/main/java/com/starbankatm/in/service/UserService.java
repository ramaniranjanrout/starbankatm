package com.starbankatm.in.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starbankatm.in.model.User;
import com.starbankatm.in.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public boolean createAccount(User user) {
        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            return false; // Username exists
        }
        userRepo.save(user);
        return true;
    }

    public Optional<User> loginWithPin(String pin) {
        return userRepo.findByPin(pin);
    }

    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public void update(User user) {
        userRepo.save(user);
    }

}
