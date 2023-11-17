package com.recruitco.custcsv.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.recruitco.custcsv.model.Customer;
import com.recruitco.custcsv.repository.CustomerRepository;
import com.recruitco.custcsv.service.CustomerServiceImpl;

public class CustomerServiceTest {

	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private CustomerServiceImpl customerService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getAllCustomersTest() {
		List<Customer> customers = Arrays.asList(
				new Customer("Oil", "EU Supplies", "Building 1", "Factory Steet", "Burnley", "Lancashire", "UK",
						"BRN 433"),
				new Customer("Aerospace Customer", "west", "Building 1", "Factory Steet", "Birmingham", "Midlands",
						"UK", "BIR 433"));

		Page<Customer> page = new PageImpl<>(customers, Pageable.unpaged(), customers.size());

		when(customerRepository.findAll(any(Pageable.class))).thenReturn(page);
		Iterable<Customer> retPage = customerService.getAllCustomers(Integer.valueOf(0), Integer.valueOf(5), "customerRef");
		List<Customer> resList = StreamSupport.stream(retPage.spliterator(), false).collect(Collectors.toList());
		assertThat(resList.get(0).getCustomerRef().equals("Oil")).isTrue();
		assertThat(resList.get(1).getCustomerRef().equals("Aerospace Customer")).isTrue();
	}

	@Test
	public void getCustomerByCustomerRefTest() {
		final Customer customer = new Customer("Software", "EU Supplies", "Building 1", "Factory Steet",
				"Burnley", "Lancashire", "UK", "BRN 433");

		when(customerRepository.findByCustomerRef(anyString())).thenReturn(customer);
		Customer customer1 = customerService.getCustomer("Software");
		assertThat(customer1.getAddressLine1().equals("Building 1")).isTrue();
	}
}
