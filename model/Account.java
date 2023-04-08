package model;

import java.util.Collection;

import dao.*;

public class Account {
    private AccountDAO dao;

    private int accountNumber;
    private int transactionID;
    private float balance;
    private Customer customer;
    private Bank bank;

    private Collection<ATMTransaction> transactions;

    public Account(AccountDAO dao, int accountNumber, float balance, Customer customer, Bank bank) {
        this.dao = dao;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.customer = customer;
        this.bank = bank;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
        dao.changeBalance(accountNumber, balance);
    }

    public Customer getCus() {
        return customer;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Collection<ATMTransaction> getTransactions() {
        if (transactions == null) {
            transactions = dao.getTransactions(transactionID);
        }
        return transactions;
    }

}
