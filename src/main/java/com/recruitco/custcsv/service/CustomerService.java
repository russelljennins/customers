package com.recruitco.custcsv.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.recruitco.custcsv.model.Customer;

@Validated
public interface CustomerService {

    @NotNull Iterable<Customer> getAllCustomers(Integer pageNo, Integer pageSize, String sortBy);

    Customer getCustomer(String customerRef);
    
    Customer create(@NotNull(message = "The customer cannot be null.") @Valid Customer customer);

    Customer update(@NotNull(message = "The customer cannot be null.") @Valid Customer customer, Long id);

	void delete(@NotNull(message = "The customer cannot be null.") @Valid Long id);
}
