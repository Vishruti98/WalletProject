package com.project.app.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

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
	static long counts = 798550;
	static Wallet recipient = null;
	static int accNum = 10030;
	private static final Double DOUBLE = (Double) null;

	// Method to create new Account
	@Override
	public int createUser(Wallet w) {
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

		return w.getAccountNumber();
	}

	// Method to get Account by using Account Id
	@Override
	public Wallet getUserById(int account) {
		try {

			Wallet w = em.find(Wallet.class, account);
			return w;
		} catch (Exception e) {
			return null;
		}
	}

	// Method to get Balance in Account
	@Override
	public double getBalance(int accNumber) {
		try {

			Wallet wallet = em.find(Wallet.class, accNumber);
			return wallet.getBalance();
		} catch (Exception e) {
			return DOUBLE;
		}
	}

	// Method to Deposit Money in Account
	@Override
	public Transaction deposit(int accountNumber, double amount) {
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

		return tr;
	}

	// Method to Withdraw Money from Account
	public Transaction withdraw(int accountNumber, double amount) {
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

		return tr;
	}

	// Method to Transfer Money
	@Override
	public Transaction transfer(double amount, int acc1, int acc2) {

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

		return tr;
	}

	// Method to print Transaction Details by Account Id
	@Override
	public List<Transaction> transactionHistory(int accountNumber) {

		Wallet wallet = em.find(Wallet.class, accountNumber);
		String qStr = "SELECT t FROM Transaction t WHERE t.accountNumber=:u";
		TypedQuery<Transaction> query = em.createQuery(qStr, Transaction.class);
		query.setParameter("u", wallet.getAccountNumber());
		List<Transaction> transactions = query.getResultList();

		return transactions;
	}

	// Method used to valid the Account Number
	@Override
	public boolean validateAccount(int account) {
		try {

			String qStr = "SELECT w FROM Wallet w WHERE w.accountNumber=:u";
			TypedQuery<Wallet> query = em.createQuery(qStr, Wallet.class);
			query.setParameter("u", account);
			Wallet wuser = query.getSingleResult();
			if (wuser.getAccountNumber() == account && wuser != null) {

				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	// Method used to validate the Password of the Account Id
	public boolean validatePassword(String pass, int accountNumber) {
		String qString = "SELECT w FROM Wallet w WHERE w.password=:password AND w.accountNumber=:accountNumber";
		TypedQuery<Wallet> query = em.createQuery(qString, Wallet.class);
		query.setParameter("password", pass);
		query.setParameter("accountNumber", accountNumber);
		try {
			Wallet wuser = query.getSingleResult();
			if (accountNumber == wuser.getAccountNumber() && wuser.getPassword().equals(pass) && wuser != null) {
				return true;
			} else {
				throw new InvalidLoginException("Account Details not entered correctly");
			}
		} catch (NoResultException e) {
			throw new InvalidLoginException("Account Details not entered correctly");
		}
	}

}
