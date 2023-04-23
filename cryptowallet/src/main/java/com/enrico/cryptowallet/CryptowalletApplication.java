package com.enrico.cryptowallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) // bypass the spring security login
//@SpringBootApplication
public class CryptowalletApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptowalletApplication.class, args);
	}

}
