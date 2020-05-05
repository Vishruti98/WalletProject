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

import com.project.app.dao.Dao;
import com.project.app.entities.Wallet;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest
class WalletBackendApplicationTests {

	@Autowired
	private Dao dao;
	Logger logger = LoggerFactory.getLogger(WalletBackendApplicationTests.class);

	@Test
	@Rollback(true)
	public void testcreateWallet() {
		logger.trace("Creation Testing Started");
		Wallet wallet = new Wallet();
		wallet.setUsername("Vishruti");
		wallet.setPassword("Vishruti@123");
		wallet.setPhoneNumber("8765376287");

		int account = dao.createUser(wallet);
		Wallet sample = dao.getUserById(account);

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
		Wallet wallet = dao.getUserById(10010);
		if (wallet == null) {
			try {}
			catch (Exception e) {
				message = e.getMessage();
				assertThat(message).isEqualTo(null);
			}
		}

		else {
			try {
				check = dao.validatePassword("Vishruti", wallet.getAccountNumber());
			} catch (Exception e) {
				message = e.getMessage();
				assertThat("Account Details not entered correctly").isEqualTo(message);
			}

			try {
				check = dao.validatePassword(wallet.getPassword(), wallet.getAccountNumber());
			} catch (Exception e) {
				message = e.getMessage();
				assertThat(true).isEqualTo(check);
			}
			
			logger.info("Account Validation Successful");
		}
	}

	@Test
	@Rollback(true)
	public void testDepositAndWithdrawMoney() {
		Wallet wallet = new Wallet();
		wallet.setUsername("Vishruti");
		wallet.setPassword("Vishruti@123");
		wallet.setPhoneNumber("8765376287");

		int account = dao.createUser(wallet);
		Wallet retrieve = dao.getUserById(account);
		
		logger.trace("Deposit Testing Started");
		double amount = 200;
		dao.deposit(account, amount);
		assertThat(wallet.getBalance()).isEqualTo(retrieve.getBalance());
		assertThat(retrieve.getBalance()).isEqualTo(200);
		logger.info("Deposit Tested Successfully");
		
		logger.trace("Withdraw Testing Started");
		double withdrawAmount = 20;
		dao.withdraw(account, withdrawAmount);
		assertThat(wallet.getBalance()).isEqualTo(retrieve.getBalance());
		assertThat(retrieve.getBalance()).isEqualTo(180);
		logger.info("Withdraw Tested Successfully");
	}

	@Test
	@Rollback(true)
	public void testTransferMoney() {
		logger.trace("Transfer Testing Started");
		Wallet wallet = new Wallet();
		wallet.setUsername("Vishruti");
		wallet.setPassword("Vishruti@123");
		wallet.setPhoneNumber("8765376287");

		int account = dao.createUser(wallet);
		Wallet retrieve = dao.getUserById(account);

		double amount = 200;
		dao.deposit(account, amount);

		Wallet wallet1 = new Wallet();
		wallet1.setUsername("Vallari");
		wallet1.setPassword("Vallari@123");
		wallet1.setPhoneNumber("7557372063");

		int account1 = dao.createUser(wallet1);
		Wallet retrieve1 = dao.getUserById(account1);

		double amount1 = 500;
		dao.deposit(account1, amount1);

		dao.transfer(100, account1, account);
		assertThat(retrieve.getBalance()).isEqualTo(100);
		assertThat(retrieve1.getBalance()).isEqualTo(600);
		logger.info("Transfer Money Tested Successfully");
	}

}
