package com.bankapp.service;

import com.bankapp.dao.AccountDAO;
import com.bankapp.dao.MySQLAccountDAO;
import com.bankapp.model.Account;

import java.math.BigDecimal;
import java.util.List;

public class AccountService {
    private AccountDAO dao;

    public AccountService() throws Exception {
        this.dao = new MySQLAccountDAO();
    }

    public Account createAccount(int customerId, String accountNumber, double initialBalance) throws Exception {
        Account a = new Account();
        a.setCustomerId(customerId);
        a.setAccountNumber(accountNumber);
        a.setBalance(BigDecimal.valueOf(initialBalance));
        return dao.create(a);
    }

    public List<Account> listAccounts() throws Exception {
        return dao.findAll();
    }

    public Account findByNumber(String accountNumber) throws Exception {
        return dao.findByAccountNumber(accountNumber);
    }

    public boolean deposit(String accountNumber, double amount) throws Exception {
        Account a = dao.findByAccountNumber(accountNumber);
        if (a == null) throw new Exception("Cuenta no encontrada");
        a.setBalance(a.getBalance().add(BigDecimal.valueOf(amount)));
        return dao.update(a);
    }

    public boolean withdraw(String accountNumber, double amount) throws Exception {
        Account a = dao.findByAccountNumber(accountNumber);
        if (a == null) throw new Exception("Cuenta no encontrada");
        if (a.getBalance().doubleValue() < amount) throw new Exception("Fondos insuficientes");
        a.setBalance(a.getBalance().subtract(BigDecimal.valueOf(amount)));
        return dao.update(a);
    }

    public boolean transfer(String from, String to, double amount) throws Exception {
        return dao.transfer(from, to, amount);
    }
}