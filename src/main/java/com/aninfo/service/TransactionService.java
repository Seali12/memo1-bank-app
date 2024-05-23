package com.aninfo.service;

import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.exceptions.TransactionNotFoundException;
import com.aninfo.model.Account;
import com.aninfo.model.Transaction;

import com.aninfo.repository.AccountRepository;
import com.aninfo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
public class TransactionService {
    // aca va la logica de las transacciones (depositos o withdraw)
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    public Transaction createTransaction(String type, Long cbu, Double amount) {
        Transaction newTransaction = new Transaction(type, cbu, amount);

        return newTransaction;
    }
    private void createDeposit(Transaction transaction) {
        if(transaction.getType() == "deposit"){
            accountService.deposit(transaction.getCbu(), transaction.getAmount());
        }
    }
    public Collection<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> searchTransactionsByCbu(Long cbu) {
        return transactionRepository.findTransactionsByCbu(cbu);
    }
    public Transaction findById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }
    public List<Transaction> findByCbu(Long cbu) {
        return transactionRepository.findTransactionsByCbu(cbu);
    }


}
