package com.project.app.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Wallet implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private int accountNumber;
	private String username;
	private String password;
	private String phoneNumber;
	private double balance;

	public Wallet() {
		super();
	}

	public Wallet(String username, String password, String phoneNumber) {
		super();
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.balance = 0.0;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

}
