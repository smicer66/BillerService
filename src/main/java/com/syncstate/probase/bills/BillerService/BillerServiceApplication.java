package com.syncstate.probase.bills.BillerService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableAutoConfiguration
public class BillerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillerServiceApplication.class, args);
	}

}
