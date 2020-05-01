package com.project.app.service;

import java.util.List;

import com.project.app.entities.Transaction;
import com.project.app.entities.Wallet;

public interface WalletService {
	public int create(Wallet w);

	public double showBalance(int account);

	public Wallet getUserById(int account);

	public Transaction deposit(int accountNumber, double amount);

	public Transaction withdraw(int accountNumber, double amount);

	public Transaction transfer(double amount, int acc1, int acc2);

	public List<Transaction> printAllTransaction(int accountNumber);

	public boolean validatePassword(String pass, int acc);

}
