package com.project.app;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.app.entities.Transaction;
import com.project.app.entities.Wallet;
import com.project.app.service.ServiceImpl;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class WalletController {

	@Autowired
	ServiceImpl serv;
	
	Logger logger= LoggerFactory.getLogger(WalletController.class);

	@PostMapping("/add")
	public int create(@RequestBody Wallet w) {
		logger.trace("Create Method Accessed...");
		return serv.create(w);
	}

	@GetMapping("/get/{AccountNumber}")
	public double showBalance(@PathVariable Integer AccountNumber) {
		logger.trace("Show Balance Method Accessed...");
		return serv.showBalance(AccountNumber);
	}

	@GetMapping("/getUser/{AccountNumber}")
	public Wallet getUserById(@PathVariable Integer AccountNumber) {
		logger.trace("Get User Method Accessed...");
		return serv.getUserById(AccountNumber);
	}

	@GetMapping("/deposit/{accountNumber}/{amount}")
	public Transaction deposit(@PathVariable int accountNumber, @PathVariable Double amount) {
		logger.trace("Deposit Method Accessed...");
		return serv.deposit(accountNumber, amount);
	}

	@GetMapping("/withdraw/{accountNumber}/{amount}")
	public Transaction withdraw(@PathVariable int accountNumber, @PathVariable Double amount) {
		logger.trace("Withdraw Method Accessed...");
		return serv.withdraw(accountNumber, amount);
	}

	@GetMapping("/transfer/{amount}/{acc1}/{acc2}")
	public Transaction transfer(@PathVariable Double amount, @PathVariable Integer acc1, @PathVariable int acc2) {
		logger.trace("Entity Method Accessed...");
		return serv.transfer(amount, acc1, acc2);
	}

	@GetMapping("/print/{accountNumber}")
	public List<Transaction> printAllTransaction(@PathVariable int accountNumber) {
		logger.trace("Print Transaction Method Accessed...");
		return serv.printAllTransaction(accountNumber);
	}

	@GetMapping("/validate/{password}/{accountNumber}")
	public boolean validatePassword(@PathVariable String password, @PathVariable int accountNumber) {
		logger.trace("Validate Method Accessed...");
		return serv.validatePassword(password, accountNumber);
	}

}
