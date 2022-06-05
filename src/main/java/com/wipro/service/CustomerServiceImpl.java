package com.wipro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.model.Customer;
import com.wipro.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Override
	public Customer updateCustomer(int customerId, Customer customer) {
		Customer c = null;
		Optional<?> cust = customerRepository.findById(customerId);
		if (cust.isPresent()) {
			c = (Customer) cust.get();
			c.setContactNumber(customer.getContactNumber());
			c.setAddress(customer.getAddress());
			c.setLastName(customer.getLastName());

			return customerRepository.save(c);
		}
		return null;
	}

	@Override
	public List<Customer> getAllCustomers() {
		List<Customer> list = customerRepository.findAll();
		if (list == null) {
			return null;
		} else {
			return list;
		}
	}

	@Override
	public Customer findCustomerbyId(int customerId) {
		Optional<?> optional = customerRepository.findById(customerId);
		if (optional.isPresent()) {
			return (Customer) optional.get();
		} else {
			return null;
		}
	}

	/*
	 * @Override public String deleteCustomer(int customerId) { Optional<?> optional
	 * = customerRepository.findById(customerId); if (optional.isPresent()) {
	 * customerRepository.delete((Customer) optional.get()); return "Delted...!"; }
	 * return null; }
	 */

}
