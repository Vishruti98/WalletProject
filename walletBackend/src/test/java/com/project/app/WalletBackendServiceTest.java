package com.project.app;

import static org.assertj.core.api.Assertions.assertThat;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.project.app.entities.Wallet;
import com.project.app.service.WalletService;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest
class WalletBackendServiceTest {
	
	@Autowired
	private WalletService service;
	Logger logger=LoggerFactory.getLogger(WalletBackendApplicationTests.class);
	
	@Test
	@Rollback(true)
	public void testcreateWallet() {
		logger.trace("Craetion Testing Started");
		Wallet wallet = new Wallet();
		wallet.setUsername("Vishruti");
		wallet.setPassword("Vishruti@123");
		wallet.setPhoneNumber("8765376287");

		int account = service.create(wallet);
		Wallet sample = service.getUserById(account);
		
		assertThat(wallet.getUsername()).isEqualTo(sample.getUsername());	
		assertThat(wallet.getPassword()).isEqualTo(sample.getPassword());
		assertThat(wallet.getBalance()).isEqualTo(sample.getBalance());
		

		logger.info("Account Creation Tested Successfully");
		}
	
	@Test
	@Rollback(true)
	public void testgetUserById() {
		logger.trace("User Testing Started");
		boolean check = false;
		String message = null;
		Wallet wallet = service.getUserById(10010);
		if (wallet == null) {
			try {}
			catch (Exception e) {
				message = e.getMessage();
				assertThat(message).isEqualTo(null);
			}
		}

		else {
			try {
				check = service.validatePassword("Vishruti", wallet.getAccountNumber());
			} catch (Exception e) {
				message = e.getMessage();
				assertThat("Account Details not entered correctly").isEqualTo(message);
			}

			try {
				check = service.validatePassword(wallet.getPassword(), wallet.getAccountNumber());
			} catch (Exception e) {
				message = e.getMessage();
				assertThat(true).isEqualTo(check);
			}
			
		}
		logger.info("Account Details Extraction Tested Successfully");
		}
	
	@Test
	@Rollback(true)
	public void testDepositAndWithdrawMoney() {
		String message=null;
		Wallet wallet = new Wallet();
		wallet.setUsername("Vishruti");
		wallet.setPassword("Vishruti@123");
		wallet.setPhoneNumber("8765376287");

		int account = service.create(wallet);
		Wallet retrieve = service.getUserById(account);
		
		logger.trace("Depsoit Testing Started");
		double amount=200;
		service.deposit(account, amount);
		assertThat(wallet.getBalance()).isEqualTo(retrieve.getBalance());
		assertThat(retrieve.getBalance()).isEqualTo(200);
		logger.info("Deposit Tested Successfully");
		
		logger.trace("Withdraw Testing Started");
		double withdrawAmount;
		try {
			 withdrawAmount=2000;
		service.withdraw(account, withdrawAmount);
		}
		catch(Exception e)
		{
			message=e.getMessage();
		}
		assertThat("Sorry! Insufficient Balance").isEqualTo(message);
		
		try {
			 withdrawAmount=20;
		service.withdraw(account, withdrawAmount);
		}
		catch(Exception e)
		{
			message=e.getMessage();
		}
		assertThat(wallet.getBalance()).isEqualTo(retrieve.getBalance());
		assertThat(retrieve.getBalance()).isEqualTo(180);
		logger.info("Witdraw Tested Successfully");
		}
	
	@Test
	@Rollback(true)
	public void testTransferMoney() {
		String message=null;
		Wallet wallet = new Wallet();
		wallet.setUsername("Vishruti");
		wallet.setPassword("Vishruti@123");
		wallet.setPhoneNumber("8765376287");

		int account = service.create(wallet);
		Wallet retrieve = service.getUserById(account);
		
		double amount=200;
		service.deposit(account, amount);
		
		
		Wallet wallet1 = new Wallet();
		wallet1.setUsername("Vallari");
		wallet1.setPassword("Vallari@123");
		wallet1.setPhoneNumber("7557372063");

		int account1 = service.create(wallet1);
		Wallet retrieve1 = service.getUserById(account1);
		
		double amount1=500;
		service.deposit(account1, amount1);
		
		logger.trace("Transfer Testing Started");		
		
		try {
			service.transfer(1000, account1, account);
		}
		catch(Exception e)
		{
			message=e.getMessage();
		}
		assertThat("Sorry! Insufficient Balance").isEqualTo(message);
		
		try {
			service.transfer(100, account1, account);
		}
		catch(Exception e)
		{
			message=e.getMessage();
		}
			
		assertThat(retrieve.getBalance()).isEqualTo(100);
		assertThat(retrieve1.getBalance()).isEqualTo(600);
		logger.info("Transfer Money Tested Successfully");
		}


	
}
