package com.example.PointOfSale.dao;

import com.example.PointOfSale.model.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerPaging extends PagingAndSortingRepository<Customer, Integer> {

}
