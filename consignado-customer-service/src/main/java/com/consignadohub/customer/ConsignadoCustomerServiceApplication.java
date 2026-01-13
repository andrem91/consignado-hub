package com.consignadohub.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ConsignadoCustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsignadoCustomerServiceApplication.class, args);
	}

}
