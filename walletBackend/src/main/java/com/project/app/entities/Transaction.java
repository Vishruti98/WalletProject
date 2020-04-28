package com.project.app.entities;


import java.time.LocalDate;
import java.time.LocalTime;



import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name="transaction")
public class Transaction {
	
@Id	
private long tId;
private int beneficiaryAccount;
private String dates;
private String time;
private String types;


private double amount;

private int  accountNumber;


LocalDate d ;
LocalTime t;

public long geTId() {
	return tId;
}



public Transaction() {
	d= LocalDate.now();  
	  t=LocalTime.now();
	this.dates =d.toString();
	this.time = t.toString();
}



public Transaction(long tId, int accountNumber, String type, int beneficiary, double amount) {
	
	this.tId = tId;
	this.accountNumber = accountNumber;
	d= LocalDate.now();  
	  t=LocalTime.now();
	this.dates =d.toString();
	this.time = t.toString();
	this.types = type;
	this.beneficiaryAccount=beneficiary;
	this.amount=amount;
}

public void setTId(long tId) {
	this.tId = tId;
}
public int getAccountNumber() {
	return accountNumber;
}
public void setAccountNumber(int accountNumber) {
	this.accountNumber = accountNumber;
}
public String getDate() {
	return dates;
}
public void setDate(String date) {
	this.dates = date;
}
public String getTime() {
	return time;
}
public void setTime(String time) {
	this.time = time;
}
public String getType() {
	return types;
}
public void setType(String type) {
	this.types = type;
}

public int getBeneficiaryAccount() {
	return beneficiaryAccount;
}

public void setBeneficiaryAccount(int beneficiaryAccount) {
	this.beneficiaryAccount = beneficiaryAccount;
}

public double getAmount() {
	return amount;
}


public void setAmount(double amount) {
	this.amount = amount;
}

}
