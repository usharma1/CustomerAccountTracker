package com.wipro.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wipro.model.Customer;
import com.wipro.repository.CustomerRepository;
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private CustomerServiceImpl customerService;
	
	@Test
	void testUpdateCustomer() {
		Customer customer=getCustomer();
		when(customerRepository.save(customer)).thenReturn(customer);
		when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
		customer.setContactNumber(8920471945l);
		customer.setAddress("Pune");
		customer.setLastName("Mehra");
		
		Customer cust=customerService.updateCustomer(customer.getCustomerId(), customer);
		assertThat(cust.getContactNumber()).isEqualTo(8920471945l);
		assertThat(cust.getAddress()).isEqualTo("Pune");
		assertThat(cust.getLastName()).isEqualTo("Mehra");
	}

	@Test
	void testGetAllCustomers() {
		Customer customer=getCustomer();
		Customer customer2=new Customer(2, "Usha", "Sharma", "Delhi", 7894561045l, "UshaADHAR");
		List<Customer> list=new ArrayList<Customer>();
		list.add(customer);
		list.add(customer2);
		when(customerRepository.findAll()).thenReturn(list);
		List<Customer> cust=customerService.getAllCustomers();
		assertEquals(cust.size(), 2);
	}

	@Test
	void testFindbyId() {
		Customer customer=getCustomer();
		when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
		Customer cust=customerService.findCustomerbyId(customer.getCustomerId());
		assertEquals(cust, customer);
	}
	
	public Customer getCustomer() {
		Customer customer=new Customer(1, "Priya", "Kumari", "Delhi", 7894561230l, "PriyaADHAR");
		return customer;
	}

}
