package com.recruitco.custcsv.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.recruitco.custcsv.model.Customer;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
	Customer findByCustomerRef(String customerRef);
}
