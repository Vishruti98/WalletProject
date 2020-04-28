package com.project.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.app.dao.Dao;
import com.project.app.dao.DaoImpl;
import com.project.app.entities.Transaction;
import com.project.app.entities.Wallet;

@Service
@Transactional
public class ServiceImpl implements WalletService {
	
@Autowired
Dao dao;
	public ServiceImpl(){
	dao=new DaoImpl();
	}
	
	@Override
	public int create(Wallet w) {
		
		return dao.createUser(w);
	}

	@Override
	public Wallet showBalance(int account) {
		// TODO Auto-generated method stub
		return dao.getBalance(account);
	}
	
	/*
	 * @Override public Wallet showBalance() {
	 * 
	 * return dao.getBalance(); }
	 */
	
	@Override
	public Transaction deposit(int accountNumber,double amount)
	{
		return dao.deposit(accountNumber,amount);
	}
	
	@Override
	public Transaction withdraw(int accountNumber,double amount)
	{
		Wallet w=dao.getBalance(accountNumber);
		double balance=(w.getBalance()-amount);
		if(balance<0.0)
			return null;
		
		return dao.withdraw(accountNumber,amount);
	}
	
	@Override
	public boolean validateUser(String user) {
		
		return dao.validateUser(user);
	} 

	@Override
	public boolean validatePassword(String pass,int acc) {
  return dao.validatePassword(pass,acc);
	}
	
	@Override
	public boolean validateAccount(int acc) {
	    
		Wallet w=dao.getUserById(acc);
		if (w!=null)
			return true;
		return false;
			
	}

	public Transaction transfer(double amount, int acc1, int acc2) {
		Wallet w=dao.getBalance(acc2);
		double balance=(w.getBalance()-amount);
		if(balance<0.0)
			return null;
		
		else
		return	dao.transfer(amount, acc1,acc2);
	
	}
	
	public List<Transaction> printAllTransaction(int accountNumber){
	
	return	dao.transactionHistory(accountNumber);
	
	}
}
