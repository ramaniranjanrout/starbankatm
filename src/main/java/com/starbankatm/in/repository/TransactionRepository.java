package com.starbankatm.in.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.starbankatm.in.model.Transaction;
import com.starbankatm.in.model.User;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

    List<Transaction> findTop5ByUserOrderByTimestampDesc(User user);
}
