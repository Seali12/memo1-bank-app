package com.aninfo.service;



import com.aninfo.exceptions.TransactionNotFoundException;
import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import com.aninfo.service.TransactionService;
import com.aninfo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.InsufficientFundsException;


import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.List;


@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    TransactionService transactionService;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Collection<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> findById(Long cbu) {
        return accountRepository.findById(cbu);
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    public void deleteById(Long cbu) {
        accountRepository.deleteById(cbu);
    }

    public List<Transaction> findTransactionsbyCbu(Long cbu){
        return transactionService.searchTransactionsByCbu(cbu);}


    @Transactional
    public Account deposit(Long cbu, Double depositValue) {

        if (depositValue <= 0) {
            throw new DepositNegativeSumException("Cannot deposit negative sums");
        }

        Account account = accountRepository.findAccountByCbu(cbu);
        account.setBalance(account.getBalance() + depositValue);
        accountRepository.save(account);

        return account;
    }



}
