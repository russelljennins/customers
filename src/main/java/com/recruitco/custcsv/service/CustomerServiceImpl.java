package com.recruitco.custcsv.service;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recruitco.custcsv.model.Customer;
import com.recruitco.custcsv.repository.CustomerRepository;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository customerRepository;

	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public Iterable<Customer> getAllCustomers(Integer pageNo, Integer pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<Customer> pagedResult = this.customerRepository.findAll(paging);
		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<Customer>();
		}
	}

	@Override
	public Customer create(Customer customer) {
		return this.customerRepository.save(customer);
	}

	@Override
	public Customer update(Customer updatedCustomer, Long id) {
		return customerRepository.findById(id).map(customer -> {
			customer.setCustomerRef(updatedCustomer.getCustomerRef());
			customer.setCustomerName(updatedCustomer.getCustomerName());
			customer.setAddressLine1(updatedCustomer.getAddressLine1());
			customer.setAddressLine2(updatedCustomer.getAddressLine2());
			customer.setTown(updatedCustomer.getTown());
			customer.setCounty(updatedCustomer.getCounty());
			customer.setCountry(updatedCustomer.getCountry());
			customer.setPostcode(updatedCustomer.getPostcode());
			return customerRepository.save(customer);
		}).orElseGet(() -> {
			return customerRepository.save(updatedCustomer);
		});
	}

	@Override
	public Customer getCustomer(String customerRef) {
		return this.customerRepository.findByCustomerRef(customerRef);
	}

	@Override
	public void delete(Long id) {
		this.customerRepository.deleteById(id);
	}
}
