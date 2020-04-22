package com.project.app;

import java.util.List;

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

@CrossOrigin(origins= "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class WalletController {
	
	@Autowired
	ServiceImpl serv;
	
	@PostMapping("/add")
	public Wallet create(@RequestBody Wallet w)
	{
		return serv.create(w);
	}
	
	@GetMapping("/get/{AccountNumber}")
	public Wallet showBalance(@PathVariable Integer AccountNumber )
	{
		return serv.showBalance(AccountNumber);
	}
	
	/*
	 * @GetMapping("/get") public Wallet showBalance() { return serv.showBalance();
	 * }
	 */
	
	@GetMapping("/deposit/{accountNumber}/{amount}")
	public Transaction deposit(@PathVariable int accountNumber,@PathVariable Double amount) {
		return serv.deposit(accountNumber,amount);
	}
	
	@GetMapping("/withdraw/{accountNumber}/{amount}")
	public Transaction withdraw(@PathVariable int accountNumber,@PathVariable Double amount) {
		return serv.withdraw(accountNumber,amount);
	}
	
	@GetMapping("/transfer/{amount}/{acc1}/{acc2}")
	public Transaction transfer(@PathVariable Double amount, @PathVariable Integer acc1,@PathVariable int acc2)
	{
		return serv.transfer(amount, acc1,acc2);
	}
	
	@GetMapping("/print/{accountNumber}")
	public List<Transaction> printAllTransaction(@PathVariable int accountNumber) {
		return serv.printAllTransaction(accountNumber);
	}
	
}
