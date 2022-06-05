package com.wipro.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.wipro.model.Customer;

@DataJpaTest
class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository customerRepository;
	

	@Test
	public void testaddCustomer() {
		Customer customer=getCustomer();
		Customer saved=customerRepository.save(customer);
		assertThat(saved).isNotNull();
		assertThat(saved.getCustomerId()>0);
	}
	
	@Test
	public void testUpdateCustomer() {
		Customer customer=getCustomer();
		Customer saved=customerRepository.save(customer);
		Customer cust=customerRepository.findById(saved.getCustomerId()).get();
		cust.setLastName("Thakur");
		cust.setContactNumber(9718617612l);
		Customer updated=customerRepository.save(cust);
		assertThat(updated.getLastName()).isEqualTo("Thakur");
		assertThat(updated.getContactNumber()).isEqualTo(9718617612l);
	}

	@Test
	public void testGetAll() {
		Customer cust1=getCustomer();
		Customer cust2=new Customer(2, "Nisha", "Sharma", "UP", 894563006l, "NishaADHAR");
		
		customerRepository.save(cust1);
		customerRepository.save(cust2);
		
		List<Customer> list=customerRepository.findAll();
		assertThat(list).isNotNull();
		assertThat(list.size()).isEqualTo(2);
	}
	
	@Test
	public void testfindById() {
		Customer customer=getCustomer();
		Customer saved=customerRepository.save(customer);
		
		Customer customer2=customerRepository.findById(saved.getCustomerId()).get();
		
		assertThat(customer2.getFirstName()).isEqualTo("Usha");
		assertThat(customer2.getAddress()).isEqualTo("Delhi");
	}
	
	public Customer getCustomer() {
		Customer customer=new Customer(1, "Usha", "Sharma", "Delhi", 9911473006l, "UshaADHAR");
		return customer;
	}
}
