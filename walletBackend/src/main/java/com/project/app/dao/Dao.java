package com.project.app.dao;

import java.util.List;

import com.project.app.entities.Transaction;
import com.project.app.entities.Wallet;

public interface Dao {
	public int createUser(Wallet w);

	public double getBalance(int account);

	public Transaction deposit(int accountNumber, double amount);

	public Transaction withdraw(int accountNumber, double amount);

	public Wallet getUserById(int account);

	public Transaction transfer(double amount, int acc1, int acc2);

	public List<Transaction> transactionHistory(int accountNumber);

	public boolean validatePassword(String pass, int account);

}
