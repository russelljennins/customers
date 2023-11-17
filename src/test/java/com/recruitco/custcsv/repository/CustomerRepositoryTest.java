package com.recruitco.custcsv.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.recruitco.custcsv.model.Customer;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTest {

	@Autowired
	CustomerRepository customerRepository;

	@Test
	public void givenCustomer_whenSave_thenGetOk() throws Exception {
		Customer customer = new Customer("Ferry", "EU Supplies", "Building 1", "Factory Steet", "Burnley",
				"Lancashire", "UK", "BRN 433");
		customerRepository.save(customer);
		Customer customer2 = customerRepository.findByCustomerRef("Ferry");

		assertAll("Customer data", () -> assertEquals("Ferry", customer2.getCustomerRef()),
				() -> assertEquals("EU Supplies", customer2.getCustomerName()),
				() -> assertEquals("Burnley", customer2.getTown()));
		customerRepository.deleteById(customer2.getId());
	}
}
