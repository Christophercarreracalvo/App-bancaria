package com.bankapp.dao;

import com.bankapp.model.Account;
import java.util.List;

public interface AccountDAO {
    Account create(Account account) throws Exception;
    Account findById(int id) throws Exception;
    Account findByAccountNumber(String accountNumber) throws Exception;
    List<Account> findAll() throws Exception;
    boolean update(Account account) throws Exception;
    boolean delete(int id) throws Exception;
    boolean transfer(String fromAccount, String toAccount, double amount) throws Exception;
}