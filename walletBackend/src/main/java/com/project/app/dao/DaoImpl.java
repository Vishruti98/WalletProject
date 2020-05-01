package com.project.app.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.app.entities.Transaction;
import com.project.app.entities.Wallet;
import com.project.app.exceptions.InvalidLoginException;

@Repository
public class DaoImpl implements Dao {

	@Autowired
	EntityManager em;
	Transaction tr;
	Transaction tr1;
	
	Logger logger=LoggerFactory.getLogger(DaoImpl.class);
	static long counts = 798550;
	static Wallet recipient = null;
	static int accNum = 10030;
	private static final Double DOUBLE = (Double) null;

	/**
	This method is used to create new Account
	@param Wallet This is the parameter to assign wallet object 
	@return int This returns the Account Number generated after account creation
	*/
	@Override
	public int createUser(Wallet w) {
		logger.trace("Create Account is accessed  at DAO layer");
		String query1 = "select count(wu.accountNumber) from Wallet wu";
		TypedQuery<Long> q1 = em.createQuery(query1, Long.class);
		long res = q1.getSingleResult();
		int res1 = (int) res;
		if (res1 > 0) {
			String query = "select max(wu.accountNumber) from Wallet wu";
			TypedQuery<Integer> q = em.createQuery(query, Integer.class);
			int result = q.getSingleResult();
			w.setAccountNumber(result + 1);
		} else
			w.setAccountNumber(accNum);
		w.setBalance(0.0);
		em.persist(w);
		logger.info("Account creation successful");
		return w.getAccountNumber();
	}

	/**
	This method is used to get User Details from Account Number
	@param account This is the parameter to specify account number 
	@return Wallet This returns the Wallet details of the Account Holder
	*/
	@Override
	public Wallet getUserById(int account) {
		logger.trace("get user by id is accessed  at DAO layer");
		try {
			logger.info("Account extraction successful");
			Wallet w = em.find(Wallet.class, account);
			return w;
		} catch (Exception e) {
			logger.error("InvalidLoginException thrown by validate method");
			return null;
		}
	}

	/**
	This method is used to check Balance of Account 
	@param accNumber This is the first parameter to specify account number 
	@return double This returns the balance value of the Account
	*/
	@Override
	public double getBalance(int accNumber) {
		logger.trace("Get Balance is accessed  at DAO layer");
		try {
			logger.info("Balance extracted successful");
			Wallet wallet = em.find(Wallet.class, accNumber);
			return wallet.getBalance();
		} catch (Exception e) {
			logger.error("InvalidLoginException thrown by validate method");
			return DOUBLE;
		}
	}

	/**
	This method is used to deposit money from Account
	@param accountNumber This is the first parameter to specify account number 
	@param amount This is the second parameter to specify the amount to deposit
	@return Transaction This returns the transaction details of the deposit
	*/
	@Override
	public Transaction deposit(int accountNumber, double amount) {
		logger.trace("Deposit is accessed  at DAO layer");
		tr = new Transaction();
		Wallet wallet = em.find(Wallet.class, accountNumber);
		System.out.println("Check point" + wallet.getBalance());
		wallet.setBalance((wallet.getBalance() + amount));
		tr.setType("credit");
		tr.setAccountNumber(wallet.getAccountNumber());
		tr.setBeneficiaryAccount(wallet.getAccountNumber());
		tr.setAmount(amount);
		String qStr = "SELECT COUNT(transaction.id) FROM Transaction transaction";
		TypedQuery<Long> query = em.createQuery(qStr, Long.class);
		Long count = query.getSingleResult();

		if (count <= 0)
			tr.setTId(counts);
		else
			tr.setTId(count + counts);

		em.persist(tr);
		logger.info("Deposit successful");
		return tr;
	}
	
	/**
	This method is used to withdraw money from Account
	@param accountNumber This is the first parameter to specify account number 
	@param amount This is the second parameter to specify the amount to withdraw
	@return Transaction This returns the transaction details of the withdrawal
	*/
	@Override
	public Transaction withdraw(int accountNumber, double amount) {
		logger.trace("Withdraw is accessed  at DAO layer");
		tr = new Transaction();

		Wallet wallet = em.find(Wallet.class, accountNumber);
		wallet.setBalance((wallet.getBalance() - amount));
		tr.setType("debit");
		tr.setAccountNumber(wallet.getAccountNumber());
		tr.setBeneficiaryAccount(wallet.getAccountNumber());
		tr.setAmount(amount);
		String qStr = "SELECT COUNT(transaction.id) FROM Transaction transaction";
		TypedQuery<Long> query = em.createQuery(qStr, Long.class);
		Long count = query.getSingleResult();

		if (count <= 0)
			tr.setTId(counts);
		else
			tr.setTId(count + counts);

		em.persist(tr);
		logger.info("Withdraw successful");
		return tr;
	}

	/**
	This method is used for transferring money from one Account to another
	@param amount This is the first parameter to specify the amount to transfer
	@param acc1 This is the second parameter to specify reciever's account number 
	@param acc2 This is the second parameter to specify sender's account number 
	@return Transaction This returns the transaction details of the transfer
	*/
	@Override
	public Transaction transfer(double amount, int acc1, int acc2) {
		logger.trace("Transfer is accessed  at DAO layer");
		Wallet wallet = em.find(Wallet.class, acc2);
		recipient = em.find(Wallet.class, acc1);

		if (recipient != null) {
			tr = new Transaction();
			tr1 = new Transaction();

			recipient.setBalance(recipient.getBalance() + amount);
			wallet.setBalance(wallet.getBalance() - amount);

			// Updating transaction of Sender's Account
			tr.setAccountNumber(wallet.getAccountNumber());
			tr.setType("Transfer");
			tr.setAmount(amount);
			tr.setBeneficiaryAccount(acc1);
			String qStr = "SELECT COUNT(transaction.id) FROM Transaction transaction";
			TypedQuery<Long> query = em.createQuery(qStr, Long.class);
			Long count = query.getSingleResult();
			if (count <= 0)
				tr.setTId(counts);
			else
				tr.setTId(count + counts);

			em.persist(tr);

			// Updating transaction of Reciever's Account
			tr1.setAccountNumber(recipient.getAccountNumber());
			tr1.setType("Transfer");
			tr1.setAmount(amount);
			tr1.setBeneficiaryAccount(acc2);
			String qStr1 = "SELECT COUNT(transaction.id) FROM Transaction transaction";
			TypedQuery<Long> query1 = em.createQuery(qStr1, Long.class);
			Long count1 = query1.getSingleResult();
			if (count1 <= 0)
				tr1.setTId(counts);
			else
				tr1.setTId(count + counts + 1);

		}

		em.merge(tr1);
		logger.info("Transfer successful");
		return tr;
	}

	/**
	This method is used to print Transaction History of Account Holder
	@param accountNumber This is the second parameter to validate method
	@return List<Transaction> This returns the list of transactions done by the user
	*/
	@Override
	public List<Transaction> transactionHistory(int accountNumber) {
		logger.trace("Transaction History is accessed  at DAO layer");
		Wallet wallet = em.find(Wallet.class, accountNumber);
		String qStr = "SELECT t FROM Transaction t WHERE t.accountNumber=:u";
		TypedQuery<Transaction> query = em.createQuery(qStr, Transaction.class);
		query.setParameter("u", wallet.getAccountNumber());
		List<Transaction> transactions = query.getResultList();
		logger.info("Transaction Details extracted successful");
		return transactions;
	}

	/**
	This method is used for validation of Account number and password
	@param pass This is the first parameter-password to validate method
	@param accountNumber This is the second parameter to validate method
	@return boolean This returns true if the password and account no. are valid and exist in database
	@throws InvalidLoginException This exception is thrown if the account no. or password is/are invalid
	*/
	@Override
	public boolean validatePassword(String pass, int accountNumber) {
		logger.trace("Validate is accessed  at DAO layer");
		String qString = "SELECT w FROM Wallet w WHERE w.password=:password AND w.accountNumber=:accountNumber";
		TypedQuery<Wallet> query = em.createQuery(qString, Wallet.class);
		query.setParameter("password", pass);
		query.setParameter("accountNumber", accountNumber);
		try {
			Wallet wuser = query.getSingleResult();
			if (accountNumber == wuser.getAccountNumber() && wuser.getPassword().equals(pass) && wuser != null) {
				logger.info("User login successful");
				return true;
			} else {
				throw new InvalidLoginException("Account Details not entered correctly");
			}
		} catch (Exception e) {
			logger.error("InvalidLoginException thrown by validate method");
			throw new InvalidLoginException("Account Details not entered correctly");
		}
	}

}
