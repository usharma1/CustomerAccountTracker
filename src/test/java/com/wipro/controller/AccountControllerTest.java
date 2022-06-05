package com.wipro.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.model.Account;
import com.wipro.model.Customer;
import com.wipro.service.AccountService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AccountController.class)
class AccountControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	AccountService accountService;

	@Autowired
	ObjectMapper mapper;

	@Test
	void testAddAccount() throws Exception {
		String msg = "Added successfully";
		Account account = getAccount();
		when(accountService.addAccount(any(Account.class))).thenReturn(account);
		mockMvc.perform(MockMvcRequestBuilders.post("/account/add").accept(MediaType.ALL)
				.content(mapper.writeValueAsString(account)).contentType("application/json"))
				.andExpect(MockMvcResultMatchers.status().is(201))
				.andExpect(MockMvcResultMatchers.content().string(msg));
	}

	@Test
	void testUpdateAccount() throws Exception {
		int accountNumber = 101;

		Account account1 = getAccount();
		Account account2 = new Account(101, "Current", 4000,
				new Customer(1, "Nisha", "Mehra", "UP", 89204719468l, "NishaADHAR"));

		when(accountService.updateAccount(any(Integer.class), any(Account.class))).thenReturn(account1);
		mockMvc.perform(MockMvcRequestBuilders.put("/account/update/" + accountNumber).accept(MediaType.ALL)
				.content(mapper.writeValueAsString(account2)).contentType("application/json"))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(account1)));

	}

	@Test
	void testGetAllAccounts() throws Exception {
		Account account1 = getAccount();
		Account account2 = getAccount2();

		List<Account> list = Arrays.asList(account1, account2);
		when(accountService.getAllAccounts()).thenReturn(list);
		mockMvc.perform(MockMvcRequestBuilders.get("/account/getAllAccount/").accept(MediaType.ALL)
				.contentType("application/json")).andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(list)));

	}

	@Test
	void testFindAccountById() throws Exception {
		int id = 101;
		Account account = getAccount();

		when(accountService.findAccountById(any(Integer.class))).thenReturn(account);
		mockMvc.perform(MockMvcRequestBuilders.get("/account/findAccountById/" + id).contentType("application/json"))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(account)));
	}

	@Test
	void testAccountByType() throws Exception {
		String accountType = "Savings";

		Account account1 = getAccount();
		Account account2 = getAccount2();

		List<Account> list = Arrays.asList(account1, account2);
		when(accountService.accountByType(any(String.class))).thenReturn(list);
		mockMvc.perform(MockMvcRequestBuilders.get("/account/accountBytype/" + accountType).content("application/json"))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(list)));
	}

	@Test
	void testGetBalanceOf() throws Exception {
		int id = 101;
		Account account = getAccount();
		String msg = "Balance in given Account " + id + " is =" + account.getBalanceAmount();
		when(accountService.getBalanceOf(any(Integer.class))).thenReturn(msg);
		mockMvc.perform(MockMvcRequestBuilders.get("/account/getBalanceOf/" + id).content("application/json"))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().string(msg));
	}

	@Test
	void testDeleteAccount() throws Exception {
		int id = 101;
		String msg = "Deleted";
		when(accountService.deleteAccount(any(Integer.class))).thenReturn(msg);
		mockMvc.perform(MockMvcRequestBuilders.delete("/account/delete/" + id).contentType("application/json"))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().string(msg));
	}

	
	@Test
	void testDeleteAll() throws Exception {
		Account account1=getAccount2();
		Account account2=getAccount();
		List<Account> list=Arrays.asList(account1,account2);
		String msg="Deleted All Records";
		when(accountService.getAllAccounts()).thenReturn(list);
		when(accountService.deleteAll()).thenReturn(msg);
		mockMvc.perform(MockMvcRequestBuilders.delete("/account/deleteAll").contentType("application/json"))
		.andExpect(MockMvcResultMatchers.status().is(200))
		.andExpect(MockMvcResultMatchers.content().string(msg));
	}
	
	
	@Test
	void testTransferFunds() throws Exception {
		double amount = 1000;
		
		Account fromAccount=getAccount2();
		Account toAccount=getAccount();
		
		String msg = "Account balance has been updated successfully in the following accounts" + " " + fromAccount.getAccountNumber() + "and"
				+ " " + toAccount.getAccountNumber();

		when(accountService.transferFunds(any(Integer.class), any(Integer.class), any(Double.class))).thenReturn(msg);
		mockMvc.perform(MockMvcRequestBuilders.put("/account/transfer/" + fromAccount.getAccountNumber() + "/" + toAccount.getAccountNumber()+"/"+amount).accept(MediaType.ALL)
				.content(mapper.writeValueAsString(msg)).contentType("application/json"))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().string(msg));
	}

	@Test
	void testDeposite() throws Exception {
		double amount = 1000;
		int id = 101;
		Account account=getAccount();
		String msg = "Sucessfully deposited";
		when(accountService.findAccountById(101)).thenReturn(account);
		when(accountService.deposite(any(Double.class), any(Integer.class))).thenReturn(msg);
		mockMvc.perform(MockMvcRequestBuilders.put("/account/deposite/" + amount + "/" + id).accept(MediaType.ALL)
				.content(mapper.writeValueAsString(msg)).contentType("application/json"))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().string(msg));
	}

	@Test
	void testWithdraw() throws JsonProcessingException, Exception {
		double amount = 1000;
		int id = 101;
		Account account=getAccount();
		String msg = "Withdraw successully done!!";
		when(accountService.findAccountById(101)).thenReturn(account);
		when(accountService.withdraw(any(Double.class), any(Integer.class))).thenReturn(msg);
		mockMvc.perform(MockMvcRequestBuilders.put("/account/withdraw/" + amount + "/" + id).accept(MediaType.ALL)
				.content(mapper.writeValueAsString(msg)).contentType("application/json"))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().string(msg));
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
