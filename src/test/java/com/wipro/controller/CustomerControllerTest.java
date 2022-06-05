package com.wipro.controller;

import static org.assertj.core.api.Assertions.fail;
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
import com.wipro.model.Customer;
import com.wipro.service.CustomerService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CustomerController.class)
class CustomerControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	CustomerService customerService;

	@Autowired
	ObjectMapper mapper;

	@Test
	void testUpdateEmployee() throws Exception {
		int id = 1;
		Customer customer = getCustomer();

		Customer update = new Customer(1, "Nisha", "Kumari", "Gzb", 78945561230l, "NishaADHAR");
		when(customerService.updateCustomer(any(Integer.class), any(Customer.class))).thenReturn(customer);
		mockMvc.perform(MockMvcRequestBuilders.put("/customer/update/" + id).accept(MediaType.ALL)
				.content(mapper.writeValueAsString(update)).contentType("application/json"))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(customer)));

	}

	@Test
	void testGetAllCustomers() throws Exception {
		Customer customer1 = getCustomer();
		Customer customer2 = new Customer(1, "Nisha", "Kumari", "Gzb", 78945561230l, "NishaADHAR");

		List<Customer> list = Arrays.asList(customer1, customer2);
		when(customerService.getAllCustomers()).thenReturn(list);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/customer/getAll").accept(MediaType.ALL).contentType("application/json"))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(list)));
	}

	@Test
	void findCustomerbyId() throws Exception {
		int id=1;
		Customer customer=getCustomer();
		
		when(customerService.findCustomerbyId(any(Integer.class))).thenReturn(customer);
		mockMvc.perform(MockMvcRequestBuilders.get("/customer/findCustomer/" + id).contentType("application/json"))
		.andExpect(MockMvcResultMatchers.status().is(200))
		.andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(customer)));
}

	public Customer getCustomer() {
		Customer customer = new Customer(1, "Priya", "Kumari", "Delhi", 7894561230l, "PriyaADHAR");
		return customer;
	}

}
