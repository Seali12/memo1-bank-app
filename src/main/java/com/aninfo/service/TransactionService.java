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
        // Validar que la transacción no sea nula
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        // Validar que el tipo sea "deposit"
        if (transaction.getType() == null || !transaction.getType().equalsIgnoreCase("deposit")) {
            throw new IllegalArgumentException("Transaction type must be 'deposit'");
        }

        return  transactionRepository.save(transaction);
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

    public Transaction withdraw(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        // Validar que el tipo  withdraw
        if (transaction.getType() == null || !transaction.getType().equalsIgnoreCase("withdraw")) {
            throw new IllegalArgumentException("Transaction type must be 'withdraw'");
        }
        return  transactionRepository.save(transaction);
    }
    public void deleteTransaction(Transaction transaction) {
        transactionRepository.deleteById(transaction.getTransactionID());
    }

}
