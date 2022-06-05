package com.wipro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.Exception.NoDataException;
import com.wipro.Exception.NotFoundException;
import com.wipro.model.Customer;
import com.wipro.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService service;

	@PutMapping("/update/{customerId}")
	public ResponseEntity<?> updateEmployee(@PathVariable("customerId") int customerId, @RequestBody Customer customer)
			throws NotFoundException {
		Customer c = service.updateCustomer(customerId, customer);
		if (c != null) {
			return new ResponseEntity<>(c, HttpStatus.OK);
		} else {
			throw new NotFoundException("Customer Not Found");
		}
	}

	@GetMapping("/getAll")
	public ResponseEntity<?> getAllCustomers() throws NoDataException {
		List<Customer> list = service.getAllCustomers();
		if (!(list.isEmpty())) {
			return new ResponseEntity<>(list, HttpStatus.OK);
		} else {
			throw new NoDataException("Empty data");
		}
	}

	@GetMapping("/findCustomer/{customerId}")
	public ResponseEntity<?> findCustomerbyId(@PathVariable("customerId") int customerId) throws NotFoundException {
		Customer customer = service.findCustomerbyId(customerId);
		if (customer != null) {
			return new ResponseEntity<>(customer, HttpStatus.OK);
		} else {
			throw new NotFoundException("Customer not found ");
		}
	}

}
