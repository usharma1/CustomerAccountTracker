package com.wipro.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.model.Account;
import com.wipro.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountRepository;

	@Override
	public Account addAccount(Account account) {
		if (account != null) {
			return accountRepository.save(account);
		} else {
			return null;
		}
	}
	
	//SaveAll();
	
	@Override
	public List<Account> addListAccount(List<Account> account) {
		List<Account> list=new ArrayList<>();
		for(int i=0;i<account.size();i++) {
			list.addAll(account);
		}
		return accountRepository.saveAll(list);
	}

	@Override
	public Account updateAccount(int accountNumber, Account account) {
		Account acc = null;
		Optional<?> o = accountRepository.findById(accountNumber);
		if (o != null) {
			acc = (Account) o.get();
			acc.setAccountType(account.getAccountType());
			acc.setBalanceAmount(account.getBalanceAmount());
			acc.setCustomer(account.getCustomer());
			return accountRepository.save(acc);
		}
		return null;
	}

	@Override
	public List<Account> getAllAccounts() {
		List<Account> list = accountRepository.findAll();
		if (list == null) {
			return null;
		} else {
			return list;
		}
	}

	@Override
	public Account findAccountById(int accountNumber) {
		Optional<?> account = accountRepository.findById(accountNumber);
		if (account.isPresent()) {
			return (Account) account.get();
		} else {
			return null;
		}
	}

	@Override
	public List<Account> accountByType(String accountType) {
		List<Account> x = accountRepository.findByAccountType(accountType);
		return x;
	}

	@Override
	public String deleteAccount(int accountNumber) {
		Optional<?> op = accountRepository.findById(accountNumber);
		if (op.isPresent()) {
			accountRepository.deleteById(accountNumber);
			return "Deleted..";
		} else {
			return null;
		}
	}

	@Override
	public String getBalanceOf(int accountNumber) {
		Optional<Account> account = accountRepository.findById(accountNumber);
		if (account.isPresent()) {
			double acc = account.get().getBalanceAmount();
			return "Balance in given Account " + accountNumber + " is =" + acc;
		} else {
			return null;
		}
	}

	@Override
	public String transferFunds(int from, int to, double amount) {

		Account fid = findAccountById(from);
		Account tid = findAccountById(to);

		Account fromAccount = accountRepository.getById(from);
		Account toAccount = accountRepository.getById(to);

		if (fid != null && tid != null) {

			if (fid == tid) {
				return "Sorry Can not transfer is same account";
			}
			if (fromAccount == null) {
				return "account [accountNumber=" + from + "] can't be found";
			}
			if (toAccount == null) {
				return "account [accountId=" + to + "] can't be found";
			} else if (fromAccount.getBalanceAmount() > amount) {
				fromAccount.setBalanceAmount(fromAccount.getBalanceAmount() - amount);
				toAccount.setBalanceAmount(toAccount.getBalanceAmount() + amount);
				accountRepository.save(fromAccount);
				accountRepository.save(toAccount);
				return "Account balance has been updated successfully in the following accounts" + " " + from + "and"
						+ " " + to;
			} else {
				return "acoount[" + from + "]  does not have the enough balance";
			}
		} else {
			return null;
		}
	}

	@Override
	public String deposite(double amount, int accountNumber) {
		Account account = accountRepository.getById(accountNumber);
		if (account == null) {
			return "account [accountNumber=" + accountNumber + "] can't be found";
		} else {
			account.setBalanceAmount(account.getBalanceAmount() + amount);
			accountRepository.save(account);
			return "Sucessfully deposited";
		}

	}

	@Override
	public String withdraw(double amount, int accountNumber) {
		Account account = accountRepository.getById(accountNumber);
		if (account == null) {
			return "account [accountNumber=" + accountNumber + "] can't be found";
		} else if (account.getBalanceAmount() < amount) {
			return "Insufficiant balance";
		} else {
			account.setBalanceAmount(account.getBalanceAmount() - amount);
			accountRepository.save(account);
			return "Withdraw successully done!!";
		}
	}

	@Override
	public String deleteAll() {
		List<Account> account = accountRepository.findAll();
		if (account != null) {
			accountRepository.deleteAll();
			return "Deleted..";
		} else {
			return null;
		}
	}

}
