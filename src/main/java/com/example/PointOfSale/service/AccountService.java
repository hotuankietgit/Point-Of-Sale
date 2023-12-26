package com.example.PointOfSale.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.PointOfSale.dao.AccountRepository;
import com.example.PointOfSale.model.Account;
import com.example.PointOfSale.model.Orders;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
@Service
public class AccountService {
	@Autowired
	private AccountRepository accountRepository;
	
	public Account getAccountByUsername(String username) {
		return accountRepository.getAccountByUsername(username);
	}
}
