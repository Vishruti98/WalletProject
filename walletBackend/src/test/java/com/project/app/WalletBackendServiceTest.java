package com.project.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
		Wallet wallet = service.getUserById(10030);
		System.out.println(wallet.getUsername());
		System.out.println(wallet.getPassword());
		assertThat(wallet.getUsername()).isEqualTo("Vishruti Malviya");
		assertThat(wallet.getPassword()).isEqualTo("Vish@123");

		logger.info("Account Details Extraction Tested Successfully");
		}
	
	@Test
	@Rollback(true)
	public void testDepositAndWithdrawMoney() {
		Wallet wallet = new Wallet();
		wallet.setUsername("Vishruti");
		wallet.setPassword("Vishruti@123");
		wallet.setPhoneNumber("8765376287");

		int account = service.create(wallet);
		Wallet retrieve = service.getUserById(account);
		
		double amount=200;
		service.deposit(account, amount);
		assertThat(wallet.getBalance()).isEqualTo(retrieve.getBalance());
		assertThat(retrieve.getBalance()).isEqualTo(200);
		logger.info("Deposit Tested Successfully");
		
		double withdrawAmount=20;
		service.withdraw(account, withdrawAmount);
		assertThat(wallet.getBalance()).isEqualTo(retrieve.getBalance());
		assertThat(retrieve.getBalance()).isEqualTo(180);
		logger.info("Deposit Tested Successfully");
		}
	
	@Test
	@Rollback(true)
	public void testTransferMoney() {
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
		
		service.transfer(100, account1, account);
		assertThat(retrieve.getBalance()).isEqualTo(100);
		assertThat(retrieve1.getBalance()).isEqualTo(600);
		logger.info("Transfer Money Tested Successfully");
		}


	
}
