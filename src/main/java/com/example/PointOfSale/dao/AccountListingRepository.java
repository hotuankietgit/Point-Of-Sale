package com.example.PointOfSale.dao;

import com.example.PointOfSale.model.Account;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountListingRepository extends PagingAndSortingRepository<Account, String> {
    List<Account> findAll();


}
