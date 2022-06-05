package com.wipro.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.wipro.model.Account;
import com.wipro.model.Customer;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AccountRepositoryTest {

	@Autowired
	private AccountRepository accountRepository;

	@Test
	public void testaddAccount() {
		Account account = getAccount();
		Account saved = accountRepository.save(account);
		assertThat(saved).isNotNull();
		assertTrue(saved.getAccountNumber() > 0);
		assertThat(account.getAccountNumber()).isEqualTo(101);

	}

	@Test
	public void testupdateAccount() {
		Account account = getAccount();
		Account saved = accountRepository.save(account);
		Account acc = accountRepository.findById(saved.getAccountNumber()).get();
		acc.setAccountType("Current");
		acc.setBalanceAmount(1000);

		Account updated = accountRepository.save(acc);
		assertThat(updated.getAccountType()).isEqualTo("Current");
		assertThat(updated.getBalanceAmount()).isEqualTo(1000);
	}

	@Test
	public void testGetAll() {
		Account account = getAccount();
		Account account2 = new Account(102, "Current", 2000,
				new Customer(2, "Nisha", "Sharma", "UP", 894563006l, "NishaADHAR"));
		accountRepository.save(account);
		accountRepository.save(account2);
		List<Account> list = accountRepository.findAll();
		assertThat(list).isNotNull();
		assertThat(list.size()).isEqualTo(2);
	}

	@Test
	public void testfindAccountById() {
		Account account = getAccount();
		Account saved = accountRepository.save(account);
		Account acc = accountRepository.findById(saved.getAccountNumber()).get();

		assertThat(acc.getAccountType()).isEqualTo("Savings");
		assertThat(acc.getBalanceAmount()).isEqualTo(2000);

	}

	@Test
	public void testDeleteAccount() {
		Account account = getAccount();
		Account account2 = new Account(102, "Current", 2000,
				new Customer(2, "Nisha", "Sharma", "UP", 894563006l, "NishaADHAR"));
		Account account3 = new Account(103, "Current", 4000,
				new Customer(3, "Priya", "Sharma", "UP", 456963006l, "PriyaADHAR"));
		Account save1 = accountRepository.save(account);
		Account save2 = accountRepository.save(account2);
		Account save3 = accountRepository.save(account3);

		accountRepository.deleteById(save1.getAccountNumber());
		List<Account> list = accountRepository.findAll();
		assertThat(list).isNotNull();
		assertThat(list.size()).isEqualTo(2);
	}

	@Test
	public void testDeleteAllAccounts() {
		Account account1 = getAccount();
		Account account2 = new Account(102, "Current", 2000,
				new Customer(2, "Nisha", "Sharma", "UP", 894563006l, "NishaADHAR"));
		List<Account> list = Arrays.asList(account1, account2);
		accountRepository.saveAll(list);
		accountRepository.deleteAll();
		assertThat(accountRepository.count()).isEqualTo(0);
	}

	@Test
	public void testfindByAccountType() {
		Account account = getAccount();
		Account account2 = new Account(102, "Current", 2000,
				new Customer(2, "Nisha", "Sharma", "UP", 894563006l, "NishaADHAR"));

		accountRepository.save(account);
		accountRepository.save(account2);

		List<Account> list = accountRepository.findByAccountType("Current");
		assertThat(list).isNotNull();
		assertThat(list.size()).isEqualTo(1);

	}

	public Account getAccount() {
		Account account = new Account(101, "Savings", 2000,
				new Customer(1, "Usha", "Sharma", "Delhi", 9911473006l, "UshaADHAR"));
		return account;
	}
}
