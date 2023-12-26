package com.example.PointOfSale.service;

import com.example.PointOfSale.dao.AccountRepository;
import com.example.PointOfSale.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AccountService {
	@Autowired
	private AccountRepository accountRepository;
	
	public Account getAccountByUsername(String username) {
		return accountRepository.getAccountByUsername(username);
	}

	public void updateAccount(Account account){
		accountRepository.save(account);
	}
}
