package com.wipro.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URISyntaxException;
import java.util.List;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wipro.ZCustomerAccountTrackerApplication;
import com.wipro.model.Account;
import com.wipro.model.Customer;
import com.wipro.repository.AccountRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ZCustomerAccountTrackerApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class AccountIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Mock
	private AccountRepository accountRepository;

	@Test
	void testAddAccount() throws URISyntaxException, JSONException, JsonProcessingException {
		Account account = new Account(101, "Savings", 2000,
				new Customer(1, "Usha", "Sharma", "Delhi", 9911473006l, "UshaADHAR"));
		ResponseEntity<?> responseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/account/add",
				account, String.class);
		System.out.println(responseEntity);
		assertEquals("Added successfully", responseEntity.getBody());
		assertEquals(201, responseEntity.getStatusCodeValue());

	}

	@Test
	public void testgetById() throws Exception {
		Account account2 = testRestTemplate.getForObject("http://localhost:" + port + "/account/findById/1",
				Account.class);

		System.out.println(account2.getAccountNumber());
		assertNotNull(account2);
	}

	
	@Test
	public void testGetAll() throws JsonProcessingException {

		ResponseEntity<?> response = testRestTemplate
				.getForEntity("http://localhost:" + port + "/account/getAllAccount", List.class);
		System.out.println(response);
		assertNotNull(response);

	}

	@Test
	public void deleteTest() throws JsonProcessingException {
		ResponseEntity<Void> responseEntity = testRestTemplate
				.exchange("http://localhost:" + port + "/account/delete/1", HttpMethod.DELETE, null, Void.class);
		int status = responseEntity.getStatusCodeValue();
		assertEquals(200, status);

	}

}
