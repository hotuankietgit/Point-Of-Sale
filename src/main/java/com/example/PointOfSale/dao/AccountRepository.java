package com.example.PointOfSale.dao;

import com.example.PointOfSale.model.Account;
import com.example.PointOfSale.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AccountRepository extends CrudRepository<Account, String> {
    Account getAccountByEmail(String email);
    Account getAccountByUsername(String username);

    List<Account> findAll();

    Account findByEmail(String email);
}
