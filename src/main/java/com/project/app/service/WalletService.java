package com.project.app.service;

import java.util.List;

import com.project.app.entities.Transaction;
import com.project.app.entities.Wallet;

public interface WalletService {
public int create(Wallet w);
public Wallet showBalance(int account);
//public Wallet showBalance();
public Transaction deposit(int accountNumber,double amount);
public Transaction withdraw(int accountNumber,double amount);
public Transaction transfer(double amount, int acc1,int acc2);
public List<Transaction> printAllTransaction(int accountNumber);


public boolean validateUser(String user);
public boolean validatePassword(String pass,int acc); 
public boolean validateAccount(int account);
}
