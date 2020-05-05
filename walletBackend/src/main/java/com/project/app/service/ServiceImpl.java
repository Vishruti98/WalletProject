package com.project.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.app.dao.Dao;
import com.project.app.dao.DaoImpl;
import com.project.app.entities.Transaction;
import com.project.app.entities.Wallet;
import com.project.app.exceptions.InsufficientBalanceException;
import com.project.app.exceptions.InvalidLoginException;

@Service
@Transactional
public class ServiceImpl implements WalletService {

	@Autowired
	Dao dao;
	
	Logger logger=LoggerFactory.getLogger(ServiceImpl.class);
	public ServiceImpl() {
		dao = new DaoImpl();
	}
	
	/**
	This method is used to create new Account
	@param Wallet This is the parameter to assign wallet object 
	@return int This returns the Account Number generated after calling DAO
	*/
	@Override
	public int create(Wallet w) {
		logger.trace("Create Account is accessed at Service layer");
		return dao.createUser(w);
	}
	
	/**
	This method is used to check Balance of Account 
	@param accNumber This is the first parameter to specify account number 
	@return double This returns the balance value of the Account after calling DAO
	*/
	@Override
	public double showBalance(int account) {
		logger.trace("Show Balance is accessed at Service layer");
		return dao.getBalance(account);
	}
	
	/**
	This method is used to get User Details from Account Number
	@param account This is the parameter to specify account number 
	@return Wallet This returns the Wallet details of the Account Holder after calling DAO
	*/
	@Override
	public Wallet getUserById(int account) {
		logger.trace("Get User is accessed at Service layer");
		return dao.getUserById(account);
	}
	
	/**
	This method is used to deposit money from Account
	@param accountNumber This is the first parameter to specify account number 
	@param amount This is the second parameter to specify the amount to deposit
	@return Transaction This returns the transaction details of the deposit after calling DAO
	*/
	@Override
	public Transaction deposit(int accountNumber, double amount) {
		logger.trace("Deposit is accessed  at Service layer");
		return dao.deposit(accountNumber, amount);
	}
	
	/**
	This method is used to withdraw money from Account
	@param accountNumber This is the first parameter to specify account number 
	@param amount This is the second parameter to specify the amount to withdraw
	@return Transaction This returns the transaction details of the withdrawal after calling DAO
	@throw InsufficientBalanceException if transfer amount is more than account balance
	*/
	@Override
	public Transaction withdraw(int accountNumber, double amount) {
		logger.trace("Withdraw is accessed at Service layer");
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
			logger.error("InsufficientBalanceException thrown by validate method");
			throw new InsufficientBalanceException("Sorry! Insufficient Balance");
		}
	}
	
	/**
	This method is used for validation of Account number and password
	@param pass This is the first parameter-password to validate method
	@param accountNumber This is the second parameter to validate method
	@return boolean This returns true if the password and account no. after calling DAO
	*/
	@Override
	public boolean validatePassword(String pass, int acc) {
		logger.trace("Validate is accessed at Service layer");
		return dao.validatePassword(pass, acc);
	}

	/**
	This method is used for transferring money from one Account to another
	@param amount This is the first parameter to specify the amount to transfer
	@param acc1 This is the second parameter to specify reciever's account number 
	@param acc2 This is the second parameter to specify sender's account number 
	@return Transaction This returns the transaction details of the transfer after calling DAO
	@throw InsufficientBalanceException if transfer amount is more than account balance
	*/
	public Transaction transfer(double amount, int acc1, int acc2) {
		logger.trace("Transfer is accessed at Service layer");
		try {
		double balance = (dao.getBalance(acc2) - amount);
		if (balance < 0.0)
		{
			throw new InsufficientBalanceException("Sorry! Insufficient Balance");
		}
		else
			return dao.transfer(amount, acc1, acc2);
		}
		catch(InsufficientBalanceException e){
			logger.error("InsufficientBalanceException thrown by validate method");
			throw new InsufficientBalanceException("Sorry! Insufficient Balance");
		}
		catch(Exception e){
			logger.error("Invalid details thrown by validate method");
			throw new InvalidLoginException("Sorry! Invalid Details");
		}

	}
	
	/**
	This method is used to print Transaction History of Account Holder
	@param accountNumber This is the second parameter to validate method
	@return List<Transaction> This returns the list of transactions done by the user
	*/
	public List<Transaction> printAllTransaction(int accountNumber) {
		logger.trace("Print Transaction is accessed at Service layer");
		return dao.transactionHistory(accountNumber);

	}
}
