package com.example.PointOfSale;

import com.example.PointOfSale.dao.AccountRepository;
import com.example.PointOfSale.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class PointOfSaleApplication {

	public static void main(String[] args) {
		SpringApplication.run(PointOfSaleApplication.class, args);
	}

}
