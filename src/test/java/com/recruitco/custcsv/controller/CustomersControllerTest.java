package com.recruitco.custcsv.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recruitco.custcsv.CustomerCSVApplication;
import com.recruitco.custcsv.model.Customer;
import com.recruitco.custcsv.repository.CustomerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = CustomerCSVApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@AutoConfigureTestDatabase
public class CustomersControllerTest {

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private MockMvc mvc;

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	public void getCustomers() throws IOException, Exception {
		
		mvc.perform(MockMvcRequestBuilders
	  			.get("/customers/getAll")
	  			.accept(MediaType.APPLICATION_JSON))
	      .andDo(print())
	      .andExpect(status().isOk())	
	      .andExpect(MockMvcResultMatchers.jsonPath("$.customer").exists());
	      //.andExpect(MockMvcResultMatchers.jsonPath("$.customers[*].customerRef").isNotEmpty());
	}
	
	@Test
	public void whenValidInput_thenCreateCustomer() throws IOException, Exception {
		Customer customer = new Customer("shipping", "EU Supplies", "Building 1", "Factory Steet", "Burnley",
				"Lancashire", "UK", "BRN 433");
		
		mvc.perform(
				post("/customers/create").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(customer)));
		Customer found = customerRepository.findByCustomerRef("shipping");
		assertThat(found.getCustomerName().equals(customer.getCustomerName())).isTrue();
	}
}
