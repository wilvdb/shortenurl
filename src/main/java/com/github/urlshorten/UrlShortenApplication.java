package com.github.urlshorten;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class UrlShortenApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlShortenApplication.class, args);
	}

}
