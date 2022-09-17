package com.example.jwtdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jwtdemo.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}
