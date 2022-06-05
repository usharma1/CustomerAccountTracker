package com.wipro.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wipro.model.Account;
import com.wipro.model.Customer;
import com.wipro.repository.AccountRepository;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

	@Mock
	private AccountRepository accountRepository;

	@InjectMocks
	private AccountServiceImpl accountService;

	@Test
	public void testAddAccount() {
		Account account = getAccount();
		when(accountRepository.save(account)).thenReturn(account);
		assertThat(accountService.addAccount(account)).isEqualTo(account);
	}

	@Test
	void testUpdateAccount() {
		Account account = getAccount();
		when(accountRepository.save(account)).thenReturn(account);
		when(accountRepository.findById(101)).thenReturn(Optional.of(account));
		account.setAccountType("Current");
		account.setBalanceAmount(2000);
		Account acc = accountService.updateAccount(101, account);
		assertThat(acc.getAccountType()).isEqualTo("Current");
		assertThat(acc.getBalanceAmount()).isEqualTo(2000);
	}

	@Test
	void testGetAllAccounts() {
		Account account = getAccount();
		List<Account> list = new ArrayList<Account>();
		list.add(account);
		when(accountRepository.findAll()).thenReturn(list);
		List<Account> acc = accountService.getAllAccounts();
		assertEquals(acc.size(), 1);
	}

	@Test
	public void testFindAccountById() {

		Account account = getAccount();
		when(accountRepository.findById(101)).thenReturn(Optional.of(account));
		Account acc = accountService.findAccountById(101);
		assertEquals(account, acc);
	}

	@Test
	void testAccountByType() {
		Account account = getAccount();
		List<Account> list = new ArrayList<Account>();
		list.add(account);
		when(accountRepository.findByAccountType("Savings")).thenReturn(list);
		List<Account> acc = accountService.accountByType("Savings");
		assertEquals(acc.size(), 1);
	}

	@Test
	void testDeleteAccount() {
		Account account = getAccount();
		when(accountRepository.findById(account.getAccountNumber())).thenReturn(Optional.of(account));
		doNothing().when(accountRepository).deleteById(account.getAccountNumber());
		String msg = accountService.deleteAccount(account.getAccountNumber());
		assertEquals("Deleted..", msg);
	}

	@Test
	public void testDeleteAll() {
		Account account1 = getAccount();
		Account account2 = getAccount2();
		List<Account> list = Arrays.asList(account1, account2);
		when(accountRepository.findAll()).thenReturn(list);
		doNothing().when(accountRepository).deleteAll();
		Object delete = accountService.deleteAll();
		assertEquals(delete, "Deleted..");
	}

	@Test
	void testGetBalanceOf() {
		Account account = getAccount();
		when(accountRepository.findById(account.getAccountNumber())).thenReturn(Optional.of(account));
		String acc = accountService.getBalanceOf(account.getAccountNumber());
		assertEquals("Balance in given Account " + account.getAccountNumber() + " is =" + 2000.0, acc);
	}

	@Test
	void testTransferFunds() {

		double amount = 1000;
		Account fromAccount = getAccount2();
		Account toAccount = getAccount();

		when(accountService.addAccount(fromAccount)).thenReturn(fromAccount);
		when(accountService.addAccount(toAccount)).thenReturn(toAccount);

		Account fid = getAccount();
		Account tid = getAccount2();
		when(accountRepository.findById(fid.getAccountNumber())).thenReturn(Optional.of(fid));
		when(accountRepository.findById(tid.getAccountNumber())).thenReturn(Optional.of(tid));

		when(accountRepository.getById(fromAccount.getAccountNumber())).thenReturn(fromAccount);
		when(accountRepository.getById(toAccount.getAccountNumber())).thenReturn(toAccount);

		fromAccount.setBalanceAmount(fromAccount.getBalanceAmount() - amount);
		toAccount.setBalanceAmount(toAccount.getBalanceAmount() + amount);
		when(accountRepository.save(fromAccount)).thenReturn(fromAccount);
		when(accountRepository.save(toAccount)).thenReturn(toAccount);

		String msg = accountService.transferFunds(fromAccount.getAccountNumber(), toAccount.getAccountNumber(), amount);
		assertEquals("Account balance has been updated successfully in the following accounts" + " "
				+ fromAccount.getAccountNumber() + "and" + " " + toAccount.getAccountNumber(), msg);
	}

	@Test
	void testDeposite() {
		double amount = 1000;
		Account account = getAccount();
		when(accountRepository.getById(account.getAccountNumber())).thenReturn(account);
		account.setBalanceAmount(account.getBalanceAmount() + amount);
		when(accountRepository.save(account)).thenReturn(account);
		String msg = accountService.deposite(amount, 101);
		assertEquals("Sucessfully deposited", msg);

	}

	@Test
	void testWithdraw() {
		double amount = 1000;
		Account account = getAccount();
		when(accountRepository.getById(account.getAccountNumber())).thenReturn(account);
		account.setBalanceAmount(account.getBalanceAmount() - amount);
		when(accountRepository.save(account)).thenReturn(account);
		String msg = accountService.withdraw(amount, 101);
		assertEquals("Withdraw successully done!!", msg);
	}

	public Account getAccount() {
		Account account = new Account(101, "Savings", 2000,
				new Customer(1, "Usha", "Sharma", "Delhi", 9911473006l, "UshaADHAR"));
		return account;
	}

	public Account getAccount2() {
		Account account = new Account(102, "Current", 4000,
				new Customer(2, "Nisha", "Mehra", "UP", 89204719468l, "NishaADHAR"));
		return account;
	}

}
