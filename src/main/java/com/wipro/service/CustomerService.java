package com.wipro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wipro.model.Customer;

@Service
public interface CustomerService {

	public Customer updateCustomer(int customerId, Customer customer);

	public List<Customer> getAllCustomers();

	public Customer findCustomerbyId(int customerId);

	//public String deleteCustomer(int customerId);
}
