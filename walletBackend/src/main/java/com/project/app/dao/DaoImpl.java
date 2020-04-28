package com.project.app.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.app.entities.Transaction;
import com.project.app.entities.Wallet;

@Repository
public class DaoImpl implements Dao {
    static Wallet recipient=null;
	//static Wallet walletUser=null;
	
	@Autowired
   EntityManager em;
   Transaction tr;
   static long counts=798550;

	/* 
	 * public DaoImpl(){ em=JPAUtil.getEntityManager();
	 * 
	 * }
	 */
	
	@Override
public int createUser(Wallet w)
{
//	em.getTransaction().begin();	
		
	em.persist(w);
//	em.getTransaction().commit();
	return w.getAccountNumber();
}
	
	@Override
	public Wallet getUserById(int account) {
	try {
		//em.getTransaction().begin();	
		Wallet w =em.find(Wallet.class, account);
		recipient=w;
		//em.getTransaction().commit();
		return w;
	}
	catch(Exception e)
	{
		return null;
	}
	}

	@Override 
	public Wallet getBalance(int accNumber) {
      try {
		//em.getTransaction().begin();
		Wallet wallet=em.find(Wallet.class, accNumber);
      //em.getTransaction().commit();
		return wallet;
      }
      catch(Exception e)
  	{
  		return null;
  	}
}
	
	
	
@Override 
public Transaction deposit(int accountNumber,double amount) {
   tr=new Transaction();	
	//em.getTransaction().begin();
   Wallet wallet=em.find(Wallet.class, accountNumber);
	System.out.println("Check point"+wallet.getBalance());
	wallet.setBalance((wallet.getBalance()+amount));
	tr.setType("credit");
	tr.setAccountNumber(wallet.getAccountNumber());
   tr.setBeneficiaryAccount(wallet.getAccountNumber());
	tr.setAmount(amount);
	
	String qStr = "SELECT COUNT(transaction.id) FROM Transaction transaction";
	TypedQuery<Long> query = em.createQuery(qStr,Long.class);
	Long count = query.getSingleResult();

if(count<=0)
	tr.setTId(counts);
else
	tr.setTId(count+counts);
	
em.persist(tr);	
//em.getTransaction().commit();
	
	return tr;
}
	
public Transaction withdraw (int accountNumber,double amount) {
	  tr=new Transaction();	
	//	em.getTransaction().begin();
	  Wallet wallet=em.find(Wallet.class, accountNumber);
		wallet.setBalance((wallet.getBalance()-amount));
		tr.setType("debit");
		tr.setAccountNumber(wallet.getAccountNumber());
        tr.setBeneficiaryAccount(wallet.getAccountNumber());
		tr.setAmount(amount);
		String qStr = "SELECT COUNT(transaction.id) FROM Transaction transaction";
		TypedQuery<Long> query = em.createQuery(qStr,Long.class);
		Long count = query.getSingleResult();

	if(count<=0)
		tr.setTId(counts);
	else
		tr.setTId(count+counts);
		
	em.persist(tr);	
	
	//em.getTransaction().commit();
		
		return tr;
}


@Override
public Transaction transfer(double amount, int acc1,int acc2) {
	//em.getTransaction().begin();
    
	Wallet wallet=em.find(Wallet.class, acc2);
	recipient=em.find(Wallet.class, acc1);
	

	if(recipient!=null)
	{
		tr =new Transaction();
		recipient.setBalance(recipient.getBalance()+amount);
		wallet.setBalance(wallet.getBalance()-amount);
		
		tr.setAccountNumber(wallet.getAccountNumber());
		tr.setType("Transfer");
		tr.setAmount(amount);
		tr.setBeneficiaryAccount(acc1);
		String qStr = "SELECT COUNT(transaction.id) FROM Transaction transaction";
		TypedQuery<Long> query = em.createQuery(qStr,Long.class);
		Long count = query.getSingleResult();

	if(count<=0)
		tr.setTId(counts);
	else
		tr.setTId(count+counts);
	
	}

	em.persist(tr);
	//em.getTransaction().commit();
	return tr;
}


@Override
public List<Transaction> transactionHistory(int accountNumber) {
	//em.getTransaction().begin();
	Wallet wallet=em.find(Wallet.class, accountNumber);
	String qStr = "SELECT t FROM Transaction t WHERE t.accountNumber=:accountNumber";
	TypedQuery<Transaction> query = em.createQuery(qStr, Transaction.class);
	query.setParameter("accountNumber", accountNumber);
	List<Transaction> transactions = query.getResultList();
	
	//em.getTransaction().commit();
	return transactions;
}




@Override	
public boolean	validateUser(String user){
try {	
	//em.getTransaction().begin();
	String qStr = "SELECT w FROM Wallet w WHERE w.username=:u";
	TypedQuery<Wallet> query = em.createQuery(qStr, Wallet.class);
	query.setParameter("u", user);
	Wallet wuser = query.getSingleResult();
	if(wuser.getUsername().equals(user) && wuser!=null)
	{
	//em.getTransaction().commit();
	
//walletUser=wuser;
		return true;
	}
	else
	{
		return false;
	}
}
catch(Exception e)
{
	return false;
}
}

public boolean validatePassword(String pass, int accountNumber )
{
	try {
	//em.getTransaction().begin();
	String qString="SELECT w FROM Wallet w WHERE w.password=:password AND w.username=:username";
	TypedQuery<Wallet> query=em.createQuery(qString, Wallet.class);
	query.setParameter("password", pass);
	//query.setParameter("username", walletUser.getUsername());
	Wallet wuser=query.getSingleResult();
	
	//em.getTransaction().commit();
	//if(walletUser.getUsername()==wuser.getUsername())
	return true;
	
	//return false;
}
catch(Exception e)
{
return false;
}
}






	
}
