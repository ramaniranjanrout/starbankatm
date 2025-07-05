package com.starbankatm.in.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.starbankatm.in.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUsername(String username);
    Optional<User> findByPin(String pin); // For login
}
