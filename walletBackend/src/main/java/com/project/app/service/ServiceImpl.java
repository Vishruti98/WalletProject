package com.project.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.app.dao.Dao;
import com.project.app.dao.DaoImpl;
import com.project.app.entities.Transaction;
import com.project.app.entities.Wallet;
import com.project.app.exceptions.InsufficientBalanceException;

@Service
@Transactional
public class ServiceImpl implements WalletService {

	@Autowired
	Dao dao;

	public ServiceImpl() {
		dao = new DaoImpl();
	}

	@Override
	public int create(Wallet w) {

		return dao.createUser(w);
	}

	@Override
	public double showBalance(int account) {

		return dao.getBalance(account);
	}

	@Override
	public Wallet getUserById(int account) {

		return dao.getUserById(account);
	}

	@Override
	public Transaction deposit(int accountNumber, double amount) {
		return dao.deposit(accountNumber, amount);
	}

	@Override
	public Transaction withdraw(int accountNumber, double amount) {
		try {
		double balance = (dao.getBalance(accountNumber) - amount);
		if (balance < 0.0)
		{
			throw new InsufficientBalanceException("Sorry! Insufficient Balance");
		}
		else
			return dao.withdraw(accountNumber, amount);
		}
		catch(Exception e){
			throw new InsufficientBalanceException("Sorry! Insufficient Balance");
		}
	}

	@Override
	public boolean validatePassword(String pass, int acc) {
		return dao.validatePassword(pass, acc);
	}

	@Override
	public boolean validateAccount(int acc) {

		Wallet w = dao.getUserById(acc);
		if (w != null)
			return true;
		return false;

	}

	public Transaction transfer(double amount, int acc1, int acc2) {
		try {
		double balance = (dao.getBalance(acc2) - amount);
		if (balance < 0.0)
		{
			throw new InsufficientBalanceException("Sorry! Insufficient Balance");
		}
		else
			return dao.transfer(amount, acc1, acc2);
		}
		catch(Exception e){
			throw new InsufficientBalanceException("Sorry! Insufficient Balance");
		}

	}

	public List<Transaction> printAllTransaction(int accountNumber) {

		return dao.transactionHistory(accountNumber);

	}
}
