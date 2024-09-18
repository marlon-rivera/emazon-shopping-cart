package com.emazon.shoppingcart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EmazonShoppingCartApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmazonShoppingCartApplication.class, args);
	}

}
