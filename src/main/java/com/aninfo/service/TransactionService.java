package com.aninfo.service;

import com.aninfo.exceptions.InvalidTransactionTypeException;

import com.aninfo.model.Account;
import com.aninfo.model.Transaction;

import com.aninfo.repository.AccountRepository;
import com.aninfo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;



@Service
public class TransactionService {
    // aca va la logica de las transacciones (depositos o withdraw), las guardo, busco
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    // constructor
    public Transaction deposit(Transaction transaction) {
        return  this.transactionRepository.save(transaction);
    }



    public List<Transaction> searchTransactionsByCbu(Long cbu) {
        return transactionRepository.findTransactionsByCbu(cbu);
    }
    public Transaction findById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }



}
