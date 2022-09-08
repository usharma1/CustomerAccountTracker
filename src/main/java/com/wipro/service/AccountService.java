package com.wipro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wipro.model.Account;

@Service
public interface AccountService {

	public Account addAccount(Account account);
	
	//For SaveALL();
	public List<Account> addListAccount(List<Account> account);

	public Account updateAccount(int accountNumber, Account account);

	public Account findAccountById(int accountNumber);

	public List<Account> accountByType(String accountType);

	public List<Account> getAllAccounts();

	public String getBalanceOf(int accountNumber);
	
	public String deleteAccount(int accountNumber);

	public String transferFunds(int from, int to, double amount);
	
	public String deposite(double amount, int accountNumber);

	public String withdraw(double amount, int accountNumber);
	
	public String deleteAll();
	
}
