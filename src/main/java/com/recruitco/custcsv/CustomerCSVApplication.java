package com.recruitco.custcsv;

import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.recruitco.custcsv.model.Customer;
import com.recruitco.custcsv.repository.CustomerRepository;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// http://localhost:8080/swagger-ui.html#

@Configuration
@EnableSwagger2
@SpringBootApplication
public class CustomerCSVApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerCSVApplication.class, args);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.regex("/.*")).build().apiInfo(apiEndPointsInfo());
	}

	private ApiInfo apiEndPointsInfo() {
		return new ApiInfoBuilder().title("Spring Boot REST API").description("recruitco Customers REST API")
				.contact(new Contact("Production Support", "www.javaguides.net", "prodsupport@gmail.com"))
				.license("Apache 2.0").licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html").version("1.0.0")
				.build();
	}

	@Bean
	CommandLineRunner init(CustomerRepository customerRepository) {
		return args -> {
			Stream.of(
					new Customer("Transport Customer", "EU Supplies", "Building 1", "Factory Steet", "Burnley",
							"Lancashire", "UK", "BRN 433"),
					new Customer("Food Customer", "Southern Food", "Building 3", "New Steet", "Woking",
							"Surrey", "UK", "SUR 563"))
					.forEach(customer -> {
						customerRepository.save(customer);
					});
		};
	}
}