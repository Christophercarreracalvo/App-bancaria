package com.bankapp.model;

import java.math.BigDecimal;

public class Account {
    private int id;
    private int customerId;
    private String accountNumber;
    private BigDecimal balance;

    public Account() {}

    public Account(int id, int customerId, String accountNumber, BigDecimal balance) {
        this.id = id;
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    @Override
    public String toString() {
        return "Account{id=" + id + ", customerId=" + customerId + ", accountNumber='" + accountNumber + "', balance=" + balance + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account a = (Account) o;
        return id == a.id;
    }
}