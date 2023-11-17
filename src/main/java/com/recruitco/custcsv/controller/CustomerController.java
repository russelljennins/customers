package com.recruitco.custcsv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.recruitco.custcsv.model.Customer;
import com.recruitco.custcsv.service.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "customers")
@CrossOrigin(maxAge = 3600)
@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	/**
	 * Creates a customer.
	 * 
	 * @param customer details
	 * @return created customer
	 */
	@ApiOperation(value = "Create a customer")
	@PostMapping(value = "/customers/create", consumes = "application/json", produces = "application/json")
	public Customer createPerson(@RequestBody Customer customer) {
		return customerService.create(customer);
	}

	/**
	 * Gets the customer data for the given customer reference.
	 * 
	 * @param name
	 * @return customer data
	 */
	@ApiOperation(value = "Gets customer derails for a given reference")
	@RequestMapping(value = "/customers/{customerRef}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> getCustomerData(@PathVariable String customerRef) {

		try {
			Customer customer = customerService.getCustomer(customerRef);
			if (customer != null) {
				return ResponseEntity.status(HttpStatus.OK).body(customer);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
}